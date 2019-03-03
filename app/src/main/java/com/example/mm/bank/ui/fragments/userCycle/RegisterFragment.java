package com.example.mm.bank.ui.fragments.userCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.spinner.SpinnerCitiesAdapter;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.cities.CitiesData;
import com.example.mm.bank.data.model.user.regester.Register;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.UserInputValidation;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;


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
public class RegisterFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.Register_Fragment_Spinner_Governments)
    Spinner RegisterFragmentSpinnerGovernments;
    @BindView(R.id.Register_Fragment_Spinner_Blood_Type)
    Spinner RegisterFragmentSpinnerBloodType;
    @BindView(R.id.Register_Fragment_Spinner_Cities)
    Spinner RegisterFragmentSpinnerCities;

    @BindView(R.id.Register_Fragment_TiL_Name)
    TextInputLayout RegisterFragmentTiLName;
    @BindView(R.id.Register_Fragment_TiL_Email)
    TextInputLayout RegisterFragmentTiLEmail;
    @BindView(R.id.Register_Fragment_TiL_BirthDate)
    TextInputLayout RegisterFragmentTiLBirthDate;
    @BindView(R.id.Register_Fragment_TiL_Last_Blood_Donation)
    TextInputLayout RegisterFragmentTiLLastBloodDonation;
    @BindView(R.id.Register_Fragment_TiL_Phone)
    TextInputLayout RegisterFragmentTiLPhone;
    @BindView(R.id.Register_Fragment_TiL_Password)
    TextInputLayout RegisterFragmentTiLPassword;
    @BindView(R.id.Register_Fragment_TiL_Re_Password)
    TextInputLayout RegisterFragmentTiLRePassword;
    @BindView(R.id.toolbar_text_title)
    TextView toolbarTextTitle;

    private String mCitiesId = null;
    private String mBloodType = null;


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        toolbarTextTitle.setText(getResources().getString(R.string.register_toolbar_title));

        /* Set Blood Type to Spinner*/
        HelperMethod.setSpinnerBloodType(getContext(), RegisterFragmentSpinnerBloodType);

        /* Get Governments Using Api Call*/
        HelperMethod.getGovernments(getContext(), RegisterFragmentSpinnerGovernments);

        /* Get Cities Using Api Call*/
        getCities();


        RegisterFragmentSpinnerBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerItem item = (CustomSpinnerItem) parent.getSelectedItem();
                mBloodType = item.getSpinnerItemName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    /**
     * Extract Input ...>> Name & Email & BirthDate &
     * phone & Last Blood Donation & Password & rePassword
     */
    private void extractInputChickValidation(String citiesId, String bloodType) {
        String name = Objects.requireNonNull(RegisterFragmentTiLName.getEditText()).getText().toString();
        String email = Objects.requireNonNull(RegisterFragmentTiLEmail.getEditText()).getText().toString().trim();
        String birthDate = Objects.requireNonNull(RegisterFragmentTiLBirthDate.getEditText()).getText().toString().trim();

        String phone = Objects.requireNonNull(RegisterFragmentTiLPhone.getEditText()).getText().toString().trim();
        String lastBloodDonation = Objects.requireNonNull(RegisterFragmentTiLLastBloodDonation.getEditText()).getText().toString();
        String password = Objects.requireNonNull(RegisterFragmentTiLPassword.getEditText()).getText().toString().trim();
        String rePassword = Objects.requireNonNull(RegisterFragmentTiLRePassword.getEditText()).getText().toString().trim();

        if (!UserInputValidation.isValidName(name)) {
            RegisterFragmentTiLName.setError("Please Enter Correct Name..");
        } else if (!UserInputValidation.isValidMail(email)) {
            RegisterFragmentTiLEmail.setError("Please Enter Correct Email..");
        } else if (birthDate.isEmpty()) {
            RegisterFragmentTiLBirthDate.setError("Please Enter Your Birth Date..");
        } else if (lastBloodDonation.isEmpty()) {
            RegisterFragmentTiLLastBloodDonation.setError("Please Enter Last Blood Donation..");
        } else if (!UserInputValidation.isValidMobile(phone)) {
            RegisterFragmentTiLPhone.setError("Please Enter Correct Phone Number..");
        } else if (!UserInputValidation.isValidPassword(password)) {
            RegisterFragmentTiLPassword.setError("Please Enter Strong Password..");
        } else if (!UserInputValidation.isValidRePassword(rePassword, password)) {
            RegisterFragmentTiLRePassword.setError("Re Password Is not equal Password..");
        } else {
            doUserRegistration(name, email, birthDate, citiesId, phone, lastBloodDonation, password, rePassword, bloodType);
        }
    }

    /**
     * Do User Registration Using Api Call
     *
     * @param name
     * @param email
     * @param birthDate
     * @param citiesId
     * @param phone
     * @param lastBloodDonation
     * @param password
     * @param rePassword
     * @param bloodType
     */
    private void doUserRegistration(String name, String email, String birthDate,
                                    String citiesId, String phone, String lastBloodDonation,
                                    String password, String rePassword, String bloodType) {

        final Call<Register> registerCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .addUserRegistration(name, email, birthDate, citiesId, phone,
                        lastBloodDonation, password, rePassword, bloodType);

        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(@NonNull Call<Register> call, @NonNull Response<Register> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        HelperMethod.replaceFragments(
                                new LoginFragment(),
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                                R.id.User_Cycle_FL_Fragment_Container,
                                null,
                                null);
                    } else {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Register> call, Throwable t) {

            }
        });
    }

    /**
     * Get Cities Using Api Call
     */
    public void getCities() {

        Call<Cities> citiesCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getCities(new CitiesData().getGovernorateId());

        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(@NonNull Call<Cities> call, @NonNull final Response<Cities> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1){
                        SpinnerCitiesAdapter citiesAdapter = new SpinnerCitiesAdapter(getContext(), response.body().getData());
                        if (RegisterFragmentSpinnerCities != null) {
                            RegisterFragmentSpinnerCities.setAdapter(citiesAdapter);
                            RegisterFragmentSpinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    CitiesData data = (CitiesData) parent.getSelectedItem();
                                    mCitiesId = data.getId().toString();
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

    @OnClick(R.id.Register_fragment_Sign_up_btn)
    public void onViewClicked() {
        if (mCitiesId != null && mBloodType != null) {
            extractInputChickValidation(mCitiesId, mBloodType);
        }
    }

}
