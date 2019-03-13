package com.example.mm.bank.ui.fragments.homeCycle.home;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.spinner.CustomSpinnerAdapter;
import com.example.mm.bank.adapter.spinner.SpinnerCitiesAdapter;
import com.example.mm.bank.adapter.spinner.SpinnerGovernmentsAdapter;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.blood_type.BloodDatum;
import com.example.mm.bank.data.model.blood_type.BloodType;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.cities.CitiesData;
import com.example.mm.bank.data.model.donation.donation_create_request.DonationCreateRequest;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.governorates.GovernoratesData;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.utils.UserInputValidation;
import com.example.mm.bank.ui.activities.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.New_request_F_Blood_Type)
    Spinner NewRequestFTiLBloodType;
    @BindView(R.id.New_request_F_Spinner_Cities)
    Spinner NewRequestFSpinnerCities;
    @BindView(R.id.New_request_F_Spinner_Governments)
    Spinner NewRequestFSpinnerGovernments;

    @BindView(R.id.New_request_F_TiL_Name)
    TextInputLayout NewRequestFTiLName;
    @BindView(R.id.New_request_F_TiL_Age)
    TextInputLayout NewRequestFTiLAge;
    @BindView(R.id.New_request_F_TiL_Number_bags)
    TextInputLayout NewRequestFTiLNumberBags;
    @BindView(R.id.New_request_F_hospital_name)
    TextInputLayout NewRequestFHospitalName;
    @BindView(R.id.New_request_ET_Location_text)
    EditText NewRequestETLocationText;
    @BindView(R.id.New_request_F_TiL_Phone)
    TextInputLayout NewRequestFTiLPhone;
    @BindView(R.id.New_request_F_TiL_Notes)
    TextInputLayout NewRequestFTiLNotes;

    //var
    private int mBloodType = 0;
    private int mCitiesId = 0;

    private String mGovernment = null;

    private Double mLatitude;
    private Double mLongitude;

    public NewRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Configure Back Pressed Listener Button */
        HelperMethod.onBackPressedListener(getContext(), getActivity());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(getContext()).getLatitude().equals("")
                && !SharedPrefManager.getInstance(getContext()).getLongitude().equals("")) {

            mLatitude = Double.parseDouble(SharedPrefManager.getInstance(getContext()).getLatitude());
            mLongitude = Double.parseDouble(SharedPrefManager.getInstance(getContext()).getLongitude());
        }

        SharedPrefManager.getInstance(getContext()).setLongitude("");
        SharedPrefManager.getInstance(getContext()).setLatitude("");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_request, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Set Blood Type to Spinner*/
        getSpinnerBloodTypes();

        /* Get Governments Using Api Call*/
        getSpinnerGovernments();

        /* Get Cities Using Api Call*/
        getSpinnerCities();

        return view;
    }

    /**
     * Extract Input ...>> Name & age & bagsNum &
     * hospitalName & hospitalAddress & phone & notes
     */
    private void extractInputAndChickValidation(double latitude, double longitude) {
        String name = Objects.requireNonNull(NewRequestFTiLName.getEditText()).getText().toString();
        String age = Objects.requireNonNull(NewRequestFTiLAge.getEditText()).getText().toString();
        String bagsNum = Objects.requireNonNull(NewRequestFTiLNumberBags.getEditText()).getText().toString();
        String hospitalName = Objects.requireNonNull(NewRequestFHospitalName.getEditText()).getText().toString();
        String hospitalAddress = NewRequestETLocationText.getText().toString();
        String phone = Objects.requireNonNull(NewRequestFTiLPhone.getEditText()).getText().toString();
        String notes = Objects.requireNonNull(NewRequestFTiLNotes.getEditText()).getText().toString();

        if (!UserInputValidation.isValidName(name)) {
            NewRequestFTiLName.setError("Please Enter Correct Name..");

        } else if (!UserInputValidation.isValidMobile(phone)) {
            NewRequestFTiLPhone.setError("Please Enter Correct Phone Number..");

        } else {
            sendApiRequestCall(SharedPrefManager.getInstance(getContext()).getApiToken(), name, age,
                    mBloodType, bagsNum, hospitalName, hospitalAddress, mCitiesId, phone, notes, latitude, longitude);

        }
    }

    /**
     * Send Donation Request Using Api Call
     *
     * @param apiToken
     * @param name
     * @param age
     * @param mBloodType
     * @param bagsNum
     * @param hospitalName
     * @param hospitalAddress
     * @param mCitiesId
     * @param phone
     * @param notes
     * @param latitude
     * @param longitude
     */
    private void sendApiRequestCall(String apiToken, String name, String age, int mBloodType, String bagsNum,
                                    String hospitalName, String hospitalAddress, int mCitiesId, String phone,
                                    String notes, Double latitude, Double longitude) {

        Call<DonationCreateRequest> donationCreateRequestCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .donationRequestCreate(apiToken, name, age, mBloodType, bagsNum, hospitalName,
                        hospitalAddress, mCitiesId, phone, notes, latitude, longitude);

        donationCreateRequestCall.enqueue(new Callback<DonationCreateRequest>() {
            @Override
            public void onResponse(@NonNull Call<DonationCreateRequest> call, @NonNull Response<DonationCreateRequest> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonationCreateRequest> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * Get Governments Using Api Call
     */
    private void getSpinnerGovernments() {

        Call<Governorates> call = RetrofitClient
                .getInstance()
                .getApiServices()
                .getGovernments();

        call.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(@NonNull Call<Governorates> call, @NonNull Response<Governorates> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 1) {
                        SpinnerGovernmentsAdapter Adapter = new SpinnerGovernmentsAdapter(getContext(), response.body().getData());
                        if (NewRequestFSpinnerGovernments != null) {
                            NewRequestFSpinnerGovernments.setAdapter(Adapter);
                            NewRequestFSpinnerGovernments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    GovernoratesData data = (GovernoratesData) parent.getSelectedItem();
                                    mGovernment = data.getName();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Governorates> call, @NonNull Throwable t) {

            }
        });
    }


    /**
     * Get Blood Types Using Api Call
     */
    private void getSpinnerBloodTypes() {

        Call<BloodType> call = RetrofitClient
                .getInstance()
                .getApiServices()
                .getBloodTypes();

        call.enqueue(new Callback<BloodType>() {
            @Override
            public void onResponse(@NonNull Call<BloodType> call, @NonNull final Response<BloodType> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 1) {
                        CustomSpinnerAdapter Adapter = new CustomSpinnerAdapter(getContext(), response.body().getData());
                        if (NewRequestFTiLBloodType != null) {
                            NewRequestFTiLBloodType.setAdapter(Adapter);
                            NewRequestFTiLBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    BloodDatum data = (BloodDatum) parent.getSelectedItem();
                                    mBloodType = data.getId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BloodType> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * Get Cities Using Api Call
     */
    public void getSpinnerCities() {

        Call<Cities> citiesCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getCities(new CitiesData().getGovernorateId());

        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(@NonNull Call<Cities> call, @NonNull final Response<Cities> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        SpinnerCitiesAdapter citiesAdapter = new SpinnerCitiesAdapter(getContext(), response.body().getData());
                        if (NewRequestFSpinnerCities != null) {
                            NewRequestFSpinnerCities.setAdapter(citiesAdapter);
                            NewRequestFSpinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    CitiesData data = (CitiesData) parent.getSelectedItem();
                                    mCitiesId = data.getId();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cities> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.New_request_FL_Location_icon, R.id.NewRequest_fragment_send_request_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.New_request_FL_Location_icon:
                if (isServicesOK()) {
                    Intent toMapActivity = new Intent(getContext(), MapsActivity.class);
                    startActivity(toMapActivity);
                }
                break;

            case R.id.NewRequest_fragment_send_request_btn:
                if (mLongitude != null && mLatitude != null) {
                    extractInputAndChickValidation(mLatitude, mLongitude);
                } else {
                    Toast.makeText(getContext(), "pleas Select Yor Location", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private static final int ERROR_DIALOG_REQUEST = 9001;

    /**
     * Checking Google Services Version
     */
    public boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if (available == ConnectionResult.SUCCESS) {
            /* Google Services Version is Available And User Can Make Map Request */
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {

            /* on Error Occured and Can Resolve it */
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(
                    getActivity(), available, ERROR_DIALOG_REQUEST
            );
            dialog.show();

        } else {
            Toast.makeText(getContext(), "You Cant Mack Map Request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
