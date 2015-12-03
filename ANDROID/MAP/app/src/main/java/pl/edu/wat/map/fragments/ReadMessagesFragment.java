package pl.edu.wat.map.fragments;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import pl.edu.wat.map.R;
import pl.edu.wat.map.adapters.MapMessageAdapter;
import pl.edu.wat.map.model.Message;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReadMessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadMessagesFragment extends Fragment {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Button buttonConfirmRadius;
    private EditText editText;
    private MapView mapView;
    private List<Message> messages;
    private Firebase ref;
    private MapMessageAdapter adapter;
    private Location mLocation;
    private LocationManager mLocationManager;
    private boolean firtsPosition = true;

    private Marker positionMarker;

    private OnFragmentInteractionListener mListener;


    public static ReadMessagesFragment newInstance() {
        ReadMessagesFragment fragment = new ReadMessagesFragment();
        return fragment;
    }

    public ReadMessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_read_messages, container, false);
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mLocationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                10, locationListener);

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter =  new MapMessageAdapter(messages, getActivity().getLayoutInflater(), getContext());
        Firebase.setAndroidContext(getContext());
        ref = new Firebase("https://dazzling-fire-990.firebaseio.com/messages");
        buttonConfirmRadius = (Button) v.findViewById(R.id.confirm_radius);
        editText = (EditText) v.findViewById(R.id.radius);
        buttonConfirmRadius.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getContext(), "Wybrano promien=" + editText.getText(), Toast.LENGTH_LONG).show();
                Double radius = Double.parseDouble(editText.getText().toString());
                ref
                        .startAt(Double.valueOf(50 - radius).toString(), "latitude")
                        .endAt(Double.valueOf(50 + radius).toString(), "latitude");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.e("Count ", "" + snapshot.getChildrenCount());

                        messages = new ArrayList<Message>();

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Message message = postSnapshot.getValue(Message.class);
                            messages.add(message);
                        }
                        adapter.setMessages(messages);
                        mMap.clear();
                        for (Message message : messages) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(message.getLatitude(),
                                            message.getLongtitude()))
                                    .title(message.getAuthor())
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

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

    public final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            mLocation = location;
            if(positionMarker != null)positionMarker.remove();
            positionMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mLocation.getLatitude(),
                            mLocation.getLongitude()))
                    .title(getResources().getString(R.string.your_position))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
            if(firtsPosition) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 17));
                firtsPosition = false;
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
