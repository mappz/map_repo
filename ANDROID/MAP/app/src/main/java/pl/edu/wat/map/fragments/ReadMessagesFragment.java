package pl.edu.wat.map.fragments;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.messages.Messages;

import java.util.ArrayList;

import pl.edu.wat.map.R;
import pl.edu.wat.map.adapters.MapMessageAdapter;
import pl.edu.wat.map.model.Author;
import pl.edu.wat.map.model.Conversation;
import pl.edu.wat.map.model.Message;

/**
 * Fragment used as container for read messages view
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class ReadMessagesFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Button buttonConfirmRadius;
    private EditText editText;
    private MapView mapView;
    private Firebase ref;
    private MapMessageAdapter adapter;
    private Location mLocation;
    private LocationManager mLocationManager;
    private boolean firtsPosition = true;
    private Marker positionMarker;
    private ArrayList<Conversation> conversations;

    private OnFragmentInteractionListener mListener;



    public ReadMessagesFragment() {
        // Required empty public constructor
    }
    /**
     * Android OS method called when activity is created
     * @param savedInstanceState saved activity state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Android OS method called when view is created
     * @param inflater inflater
     * @param container ViewGroup container
     * @param savedInstanceState previous activity state
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_read_messages, container, false);
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mLocationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, locationListener);


        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new MapMessageAdapter(conversations, getActivity().getLayoutInflater(), getActivity());
        Firebase.setAndroidContext(getActivity());
        ref = new Firebase("https://dazzling-fire-990.firebaseio.com/conversations");

        buttonConfirmRadius = (Button) v.findViewById(R.id.confirm_radius);
        editText = (EditText) v.findViewById(R.id.radius);

        buttonConfirmRadius.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Wybrano promien=" + editText.getText(), Toast.LENGTH_LONG).show();
              /*  Double radius = Double.parseDouble(editText.getText().toString());
                ref
                        .startAt(Double.valueOf(50 - radius).toString(), "latitude")
                        .endAt(Double.valueOf(50 + radius).toString(), "latitude");*/
                ref.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.e("Count ", "" + snapshot.getChildrenCount());

                        conversations = new ArrayList<Conversation>();

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Conversation conversation = new Conversation();
                            Author author = (Author) postSnapshot.child("author").getValue(Author.class);
                            String category = (String) postSnapshot.child("category").getValue();
                            String date = (String) postSnapshot.child("date").getValue();
                            Double latitude = (Double) postSnapshot.child("latitude").getValue();
                            Double longitude = (Double) postSnapshot.child("longitude").getValue();
                            ArrayList<Messages> messages = (ArrayList) postSnapshot.child("messages").getValue();
                            String id = postSnapshot.getKey();
                            conversation.setId(id);
                            conversation.setAuthor(author);
                            conversation.setCategory(category);
                            conversation.setDate(date);
                            conversation.setLatitude(latitude);
                            conversation.setLongtitude(longitude);
                            conversation.setCategory(category);
                            conversations.add(conversation);
                        }
                        adapter.setConversations(conversations);
                        mMap.clear();
                        for (Conversation conversation : conversations) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(conversation.getLatitude(),
                                            conversation.getLongtitude()))
                                    .title(conversation.getAuthor().getName())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.message)));
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }
        });
        setMapView();
        return v;
    }


    /**
     * Android OS method called when fragment is detached
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void setMapView() {
        mapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap = mapView.getMap();
        mMap.setInfoWindowAdapter(adapter);
        mMap.setOnInfoWindowClickListener(this);
    }

    /**
     * Called when info windows is clicked
     * @param marker Map marker
	 */
    @Override
    public void onInfoWindowClick(Marker marker)
    {
        Conversation conversationToShow = null;
        if(conversations != null)
        {
            for (Conversation conversation : conversations)
            {
                if (marker.getPosition().longitude == conversation.getLongtitude() &&
                        marker.getPosition().latitude == conversation.getLatitude())
                {
                    conversationToShow = conversation;
                    break;
                }
            }
        }

        Bundle args = new Bundle();
        args.putSerializable("conversation", conversationToShow);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        SendMessagesFragment sendMessagesFragment = new SendMessagesFragment();
        sendMessagesFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, sendMessagesFragment, ReadMessagesFragment.class.getName());
        transaction.addToBackStack("FRAGMENT REPLACE");
        transaction.commit();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

	/**
     * Location listener class
     */
    public final LocationListener locationListener = new LocationListener()
    {
		/**
         * Called when location has been changed
         * @param location new Location
         */
        public void onLocationChanged(Location location)
        {
            mLocation = location;
            if (positionMarker != null) positionMarker.remove();
            positionMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mLocation.getLatitude(),
                            mLocation.getLongitude()))
                    .title(getResources().getString(R.string.your_position))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
            if (firtsPosition)
            {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 17));
                firtsPosition = false;
            }
        }

        /**
         * Called when status is changed
         * @param provider provider
         * @param status status
         * @param extras extra data
		 */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        /**
         * Called when provider is enabled
         * @param provider provider name
		 */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * Called when provider is disabled
         * @param provider provider name
		 */
        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
