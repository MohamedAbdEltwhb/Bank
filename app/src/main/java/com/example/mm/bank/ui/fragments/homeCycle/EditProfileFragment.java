package com.example.mm.bank.ui.fragments.homeCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.spinner.SpinnerCitiesAdapter;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.cities.CitiesData;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class EditProfileFragment extends Fragment {

    @BindView(R.id.EditProfile_Fragment_Spinner_Blood_Type)
    Spinner EditProfileFragmentSpinnerBloodType;
    @BindView(R.id.EditProfile_Fragment_Spinner_Governments)
    Spinner EditProfileFragmentSpinnerGovernments;
    @BindView(R.id.EditProfile_Fragment_Spinner_Cities)
    Spinner EditProfileFragmentSpinnerCities;

    @BindView(R.id.EditProfile_Fragment_TiL_Name)
    TextInputLayout EditProfileFragmentTiLName;
    @BindView(R.id.EditProfile_Fragment_TiL_Email)
    TextInputLayout EditProfileFragmentTiLEmail;
    @BindView(R.id.EditProfile_Fragment_TiL_BirthDate)
    TextInputLayout EditProfileFragmentTiLBirthDate;
    @BindView(R.id.EditProfile_Fragment_TiL_Last_Blood_Donation)
    TextInputLayout EditProfileFragmentTiLLastBloodDonation;
    @BindView(R.id.EditProfile_Fragment_TiL_Phone)
    TextInputLayout EditProfileFragmentTiLPhone;
    @BindView(R.id.EditProfile_Fragment_TiL_Password)
    TextInputLayout EditProfileFragmentTiLPassword;
    @BindView(R.id.EditProfile_Fragment_TiL_Re_Password)
    TextInputLayout EditProfileFragmentTiLRePassword;
    Unbinder unbinder;

    private String mCitiesID = null;
    private String mBloodType = null;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Set Blood Type to Spinner*/
        HelperMethod.setSpinnerBloodType(getContext(), EditProfileFragmentSpinnerBloodType);

        /* Get Governments Using Api Call*/
        HelperMethod.getGovernments(getContext(), EditProfileFragmentSpinnerGovernments);

        /* Get Cities Using Api Call*/
        getSpinnerCities();


        EditProfileFragmentSpinnerBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        if (EditProfileFragmentSpinnerCities != null) {
                            EditProfileFragmentSpinnerCities.setAdapter(citiesAdapter);
                            EditProfileFragmentSpinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    CitiesData data = (CitiesData) parent.getSelectedItem();
                                    mCitiesID = data.getId().toString();

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

    @OnClick(R.id.EditProfile_fragment_Sign_up_btn)
    public void onViewClicked() {
    }
}
