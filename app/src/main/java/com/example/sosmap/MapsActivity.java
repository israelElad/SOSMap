package com.example.sosmap;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Location myLocation;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                //your code here
                Toast.makeText(getApplicationContext(),location.toString(), Toast.LENGTH_SHORT).show();
                myLocation=location;
                if(mMap!=null){
                    onMapReady(mMap);
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
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            // here to request the missing permissions, and then overriding
            //  public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                50, mLocationListener);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        DataBase db=DataBase.getInstance();
        Map<Location,String> map=db.getLocations();
        for(Location loc : map.keySet()){
            LatLng firstAidKit = new LatLng(loc.getLatitude(), loc.getLongitude());
            mMap.addMarker(new MarkerOptions().position(firstAidKit).title(map.get(loc)));
        }

        if(myLocation!=null) {
            LatLng firstAidKit = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(firstAidKit).title("My location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(firstAidKit));


        }

    }


}
