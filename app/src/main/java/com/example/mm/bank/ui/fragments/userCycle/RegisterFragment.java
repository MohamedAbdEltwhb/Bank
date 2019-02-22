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
import com.example.mm.bank.adapter.spinner.SpinnerGovernmentsAdapter;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.cities.CitiesData;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.governorates.GovernoratesData;
import com.example.mm.bank.data.rest.ApiServices;
import com.example.mm.bank.helper.DateInputMask;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.UserInputValidation;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mm.bank.data.rest.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.Register_Fragment_Spinner_Governments) Spinner RegisterFragmentSpinnerGovernments;
    @BindView(R.id.Register_Fragment_Spinner_Blood_Type) Spinner RegisterFragmentSpinnerBloodType;
    @BindView(R.id.Register_Fragment_Spinner_Cities) Spinner RegisterFragmentSpinnerCities;

    @BindView(R.id.Register_Fragment_TiL_Name) TextInputLayout RegisterFragmentTiLName;
    @BindView(R.id.Register_Fragment_TiL_Email) TextInputLayout RegisterFragmentTiLEmail;
    @BindView(R.id.Register_Fragment_TiL_BirthDate) TextInputLayout RegisterFragmentTiLBirthDate;
    @BindView(R.id.Register_Fragment_TiL_Last_Blood_Donation) TextInputLayout RegisterFragmentTiLLastBloodDonation;
    @BindView(R.id.Register_Fragment_TiL_Phone) TextInputLayout RegisterFragmentTiLPhone;
    @BindView(R.id.Register_Fragment_TiL_Password) TextInputLayout RegisterFragmentTiLPassword;
    @BindView(R.id.Register_Fragment_TiL_Re_Password) TextInputLayout RegisterFragmentTiLRePassword;
    @BindView(R.id.toolbar_text_title) TextView toolbarTextTitle;

    private String mCitieId = null;
    private String mBloodType = null;


    private ApiServices mApiServices;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        toolbarTextTitle.setText(getResources().getString(R.string.register_toolbar_title));
        mApiServices = getClient().create(ApiServices.class);

        new DateInputMask(RegisterFragmentTiLBirthDate.getEditText());

        HelperMethod.setSpinnerBloodType(getContext(), RegisterFragmentSpinnerBloodType);

        getGovernments();
        getCities();

        RegisterFragmentSpinnerBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerItem item = (CustomSpinnerItem) parent.getSelectedItem();
                Toast.makeText(getContext(), item.getSpinnerItemName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void extractInputChickValidation() {
        String name = RegisterFragmentTiLName.getEditText().getText().toString();
        String email = RegisterFragmentTiLEmail.getEditText().getText().toString().trim();
        String birthDate = RegisterFragmentTiLBirthDate.getEditText().getText().toString().trim();

        String phone = RegisterFragmentTiLPhone.getEditText().getText().toString().trim();
        String lastBloodDonation = RegisterFragmentTiLLastBloodDonation.getEditText().getText().toString();
        String password = RegisterFragmentTiLPassword.getEditText().getText().toString().trim();
        String rePassword = RegisterFragmentTiLRePassword.getEditText().getText().toString().trim();

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
            doUserRegistration();
        }
    }

    /**
     * Do User Registration Using Api Call
     */
    private void doUserRegistration() {
    }

    /**
     * Get Cities Using Api Call
     */
    private void getGovernments(){
        Call<Governorates> call = mApiServices.getGovernments();
        call.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {

                if (response.isSuccessful()){
                    SpinnerGovernmentsAdapter Adapter = new SpinnerGovernmentsAdapter(getActivity(), response.body().getData());
                    if (RegisterFragmentSpinnerGovernments != null) {
                        RegisterFragmentSpinnerGovernments.setAdapter(Adapter);
                        RegisterFragmentSpinnerGovernments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                GovernoratesData data = (GovernoratesData) parent.getSelectedItem();
                                Toast.makeText(getContext(), data.getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });
    }

    /**
     * Get Cities Using Api Call
     */
    private void getCities(){
        Call<Cities> citiesCall = mApiServices.getCities(new CitiesData().getGovernorateId());
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.isSuccessful()){
                    SpinnerCitiesAdapter citiesAdapter = new SpinnerCitiesAdapter(getActivity(), response.body().getData());
                    if (RegisterFragmentSpinnerCities != null) {
                        RegisterFragmentSpinnerCities.setAdapter(citiesAdapter);
                        RegisterFragmentSpinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                CitiesData data = (CitiesData) parent.getSelectedItem();
                                mCitieId  = data.getId().toString();

                                Toast.makeText(getContext(), mCitieId, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

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
        extractInputChickValidation();
    }

}
