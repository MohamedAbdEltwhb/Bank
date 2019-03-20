package com.example.mm.bank.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.toolbar_text_title)
    TextView toolbarTextTitle;
    @BindView(R.id.back_Image_toolBar)
    ImageView backImageToolBar;

    private GoogleMap mMap;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Double mLatitude;
    private Double mLongitude;


    private FusedLocationProviderClient mFusedLocationProviderClient;

    private boolean mLocationPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        toolbarTextTitle.setText(getResources().getString(R.string.select_location));
        backImageToolBar.setVisibility(View.VISIBLE);

        getLocationPermission();
    }

    public void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation1 = (Location) task.getResult();
                            if (currentLocation1 != null) {

                                mLatitude = currentLocation1.getLatitude();
                                mLongitude = currentLocation1.getLongitude();

                                moveCamera(
                                        new LatLng(mLatitude, mLongitude),
                                        DEFAULT_ZOOM,
                                        "My Location"
                                );
                            }

                        } else {
                            Toast.makeText(MapsActivity.this, "Unable to get Current Location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void moveCamera(LatLng latLng, float zoom, String title) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_l);
//        final MarkerOptions markerOptions = new MarkerOptions()
//                .position(latLng)
//                .title(title)
//                .icon(icon);

        //myMarker = mMap.addMarker(markerOptions);

        //myMarker.setPosition(latLng);
        //mMap.addMarker(markerOptions);

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = mMap.getCameraPosition().target;

                mLatitude = center.latitude;
            }
        });
    }

    public void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext()
                , FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext()
                    , COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();

            } else {
                ActivityCompat.requestPermissions(
                        this,
                        permission,
                        LOCATION_PERMISSION_REQUEST_CODE
                );
            }
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    permission,
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;

                    // Initialize Our Map
                    initMap();
                }

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            mMap.setMyLocationEnabled(true);
        }
    }

    @OnClick({R.id.back_Image_toolBar, R.id.MapActivity_Select_Location_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_Image_toolBar:
                finish();
                break;

            case R.id.MapActivity_Select_Location_btn:

                Toast.makeText(MapsActivity.this, "" + mLatitude + "/" + mLongitude
                        , Toast.LENGTH_SHORT).show();

                SharedPrefManager.getInstance(this).setLatitude(mLatitude.toString());
                SharedPrefManager.getInstance(this).setLongitude(mLongitude.toString());

                finish();
                break;
        }
    }
}
