package com.example.mm.bank.ui.fragments.homeCycle.order;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.donation.donation_details.DonationDetails;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mm.bank.ui.fragments.homeCycle.order.SendDonationDetails.CLINT_ID;

public class OrderRequestInformationActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.RequestF_Order_TV_Name_value)
    TextView RequestFOrderTVNameValue;
    @BindView(R.id.RequestF_Order_TV_Age_value)
    TextView RequestFOrderTVAgeValue;
    @BindView(R.id.RequestF_Order_TV_Blood_type_value)
    TextView RequestFOrderTVBloodTypeValue;
    @BindView(R.id.RequestF_Order_TV_Number_bags_value)
    TextView RequestFOrderTVNumberBagsValue;
    @BindView(R.id.RequestF_Order_TV_hospital_value)
    TextView RequestFOrderTVHospitalValue;
    @BindView(R.id.RequestF_Order_TV_hospital_address_value)
    TextView RequestFOrderTVHospitalAddressValue;
    @BindView(R.id.RequestF_Order_TV_Phone_number_value)
    TextView RequestFOrderTVPhoneNumberValue;
    @BindView(R.id.RequestF_Order_TV_Details_value)
    TextView RequestFOrderTVDetailsValue;

    //var
    private double mLatitude;
    private double mLongitude;
    private int clintId = 0;

    private String mPhoneNumper = null;
    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int REQUEST_CALL = 1;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request_information);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        clintId = intent.getIntExtra(CLINT_ID, 0);
        if (clintId != 0) {
            donationApiDetails(clintId);
        }
    }

    private void donationApiDetails(Integer idClint) {
        Call<DonationDetails> donationDetailsCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getDonationDetails(SharedPrefManager.getInstance(this).getApiToken(), idClint);
        donationDetailsCall.enqueue(new Callback<DonationDetails>() {
            @Override
            public void onResponse(@NonNull Call<DonationDetails> call, @NonNull Response<DonationDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        RequestFOrderTVNameValue.setText(Objects.requireNonNull(response.body()).getData().getPatientName());
                        RequestFOrderTVAgeValue.setText(Objects.requireNonNull(response.body()).getData().getPatientAge());
                        RequestFOrderTVBloodTypeValue.setText(Objects.requireNonNull(response.body()).getData().getBloodType().getName());
                        RequestFOrderTVNumberBagsValue.setText(Objects.requireNonNull(response.body()).getData().getBagsNum());
                        RequestFOrderTVHospitalValue.setText(Objects.requireNonNull(response.body()).getData().getHospitalName());
                        RequestFOrderTVHospitalAddressValue.setText(Objects.requireNonNull(response.body()).getData().getHospitalAddress());

                        mPhoneNumper = response.body().getData().getPhone();
                        RequestFOrderTVPhoneNumberValue.setText(mPhoneNumper);
                        RequestFOrderTVDetailsValue.setText(Objects.requireNonNull(response.body()).getData().getNotes());

                        mLatitude = Double.valueOf(Objects.requireNonNull(response.body()).getData().getLatitude());
                        mLongitude = Double.valueOf(Objects.requireNonNull(response.body()).getData().getLongitude());

                        if (mLatitude != 0 && mLongitude != 0) {
                            initMap();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DonationDetails> call, Throwable t) {

            }
        });
    }

    private void makePhoneCall() {
        if (mPhoneNumper.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(OrderRequestInformationActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(OrderRequestInformationActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + mPhoneNumper;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(OrderRequestInformationActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mLatitude != 0 && mLongitude != 0) {
            mMap = googleMap;
            Toast.makeText(this, "" + mLatitude + "/" + mLongitude, Toast.LENGTH_SHORT).show();

            // Add a marker in Sydney and move the camera
            LatLng latLng = new LatLng(mLongitude, mLatitude);

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_l);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title("My Place")
                    .icon(icon);

            mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        }
    }

    @OnClick(R.id.RequestF_Order_Button_call)
    public void onViewClicked() {
        makePhoneCall();
    }
}
