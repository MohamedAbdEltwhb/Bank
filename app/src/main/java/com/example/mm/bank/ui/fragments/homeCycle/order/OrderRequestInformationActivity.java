package com.example.mm.bank.ui.fragments.homeCycle.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.donation.donation_details.DonationDetails;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

    private double mLatitude;
    private double mLongitude;
    private Integer clintId;
    private GoogleMap mMap;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request_information);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        clintId = intent.getIntExtra(CLINT_ID, 0);
        if (clintId != null) {
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
            public void onResponse(Call<DonationDetails> call, Response<DonationDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        RequestFOrderTVNameValue.setText(response.body().getData().getPatientName());
                        RequestFOrderTVAgeValue.setText(response.body().getData().getPatientAge());
                        RequestFOrderTVBloodTypeValue.setText(response.body().getData().getBloodType().getName());
                        RequestFOrderTVNumberBagsValue.setText(response.body().getData().getBagsNum());
                        RequestFOrderTVHospitalValue.setText(response.body().getData().getHospitalName());
                        RequestFOrderTVHospitalAddressValue.setText(response.body().getData().getHospitalAddress());
                        RequestFOrderTVPhoneNumberValue.setText(response.body().getData().getPhone());
                        RequestFOrderTVDetailsValue.setText(response.body().getData().getNotes());

                        mLatitude = Double.valueOf(response.body().getData().getLatitude());
                        mLongitude = Double.valueOf(response.body().getData().getLongitude());

                        Toast.makeText(OrderRequestInformationActivity.this, String.valueOf(mLatitude) + " / " + String.valueOf(mLongitude), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DonationDetails> call, Throwable t) {

            }
        });
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

        if (mLatitude != 0 && mLongitude != 0) {
            mMap = googleMap;

            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(mLongitude, mLatitude);

            mMap.addMarker(new MarkerOptions().position(sydney).title("My Place"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        } else {
            Toast.makeText(this, "" + mLatitude + "/" + mLongitude, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.RequestF_Order_Button_call)
    public void onViewClicked() {
    }
}
