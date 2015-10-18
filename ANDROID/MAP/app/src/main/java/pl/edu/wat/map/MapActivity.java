package pl.edu.wat.map;

import android.content.Context;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import pl.edu.wat.map.adapters.MapMessageAdapter;
import pl.edu.wat.map.model.Message;


/**
 * Created by Hubert Faszcza on 2015-10-18.
 */
public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Button buttonConfirmRadius;
    private EditText editText;
    private List<Message> messages;
    private Firebase ref;
    private MapMessageAdapter adapter;
    private LocationManager lm;
    private Location mLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        adapter =  new MapMessageAdapter(messages, getLayoutInflater());
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://dazzling-fire-990.firebaseio.com/messages");
        buttonConfirmRadius = (Button) findViewById(R.id.confirm_radius);
        editText = (EditText) findViewById(R.id.radius);
       // lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
      //  mLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       // lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(adapter);
        buttonConfirmRadius.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Wybrano promien=" + editText.getText(), Toast.LENGTH_LONG).show();
                Double radius = Double.parseDouble(editText.getText().toString());
                ref
                        .startAt(Double.valueOf(mLocation.getLatitude() - radius).toString(), "latitude")
                        .endAt(Double.valueOf(mLocation.getLatitude() + radius).toString(), "latitude")
                        .startAt(Double.valueOf(mLocation.getLongitude() - radius).toString(), "longtitude")
                        .endAt(Double.valueOf(mLocation.getLongitude() + radius).toString(), "longtitude");
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
                                    .position(message.getPosition())
                                    .title(message.getAuthor()));
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
        marker.showInfoWindow();
        return true;
    }

    public final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            mLocation = location;
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
