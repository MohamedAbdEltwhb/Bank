package com.example.mm.bank.ui.fragments.userCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.spinner.SpinnerCitiesAdapter;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.cities.CitiesData;
import com.example.mm.bank.data.model.user.profile.edit_profile_data.EditProfileData;
import com.example.mm.bank.data.model.user.profile.get_profile_data.GetProfileData;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.utils.UserInputValidation;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mm.bank.data.local.SharedPrefManager.*;

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

    //var
    private int mBloodTypeItemPosition;
    private int mCityItemPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Configure Back Pressed Listener Button */
        HelperMethod.onBackPressedListener(getContext(), getActivity());
    }

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
        HelperMethod.getSpinnerBloodTypes(getContext(), EditProfileFragmentSpinnerBloodType);

        /* Get Governments Using Api Call*/
        HelperMethod.getGovernments(getContext(), EditProfileFragmentSpinnerGovernments);

        /* Get Cities Using Api Call*/
        getSpinnerCities();

        /* Get User Information Using Api Call*/
        getUserProfileData(getInstance(getContext()).getApiToken());

        EditProfileFragmentSpinnerBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBloodTypeItemPosition = EditProfileFragmentSpinnerBloodType.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    /**
     * Get Current Profile User Data Using Api Call
     *
     * @param apiToken
     */
    private void getUserProfileData(String apiToken) {
        final Call<GetProfileData> getProfileDataCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getProfileData(apiToken);
        getProfileDataCall.enqueue(new Callback<GetProfileData>() {
            @Override
            public void onResponse(Call<GetProfileData> call, Response<GetProfileData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        EditProfileFragmentTiLName.getEditText().setText(response.body().getData().getUser().getName());
                        EditProfileFragmentTiLEmail.getEditText().setText(response.body().getData().getUser().getEmail());
                        EditProfileFragmentTiLBirthDate.getEditText().setText(response.body().getData().getUser().getBirthDate());
                        EditProfileFragmentTiLLastBloodDonation.getEditText().setText(response.body().getData().getUser().getDonationLastDate());
                        EditProfileFragmentTiLPhone.getEditText().setText(response.body().getData().getUser().getPhone());

                        EditProfileFragmentSpinnerBloodType.setSelection(SharedPrefManager.getInstance(getContext()).getSelectedBloodItemPosition());
                        EditProfileFragmentSpinnerGovernments.setSelection(SharedPrefManager.getInstance(getContext()).getSelectedGovernmentsItemPosition() - 1);
                        EditProfileFragmentSpinnerCities.setSelection(SharedPrefManager.getInstance(getContext()).getSelectedCityItemPosition());

                    } else {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetProfileData> call, Throwable t) {

            }
        });
    }

    /**
     * Extract Input ...>> Name & Email & BirthDate &
     * phone & Last Blood Donation & Password & rePassword
     *
     * @param cityId
     * @param bloodType
     * @param apiToken
     */
    private void extractInputChickValidation(int cityId, int bloodType, String apiToken) {

        String name = Objects.requireNonNull(EditProfileFragmentTiLName.getEditText()).getText().toString();
        String email = Objects.requireNonNull(EditProfileFragmentTiLEmail.getEditText()).getText().toString().trim();
        String birthDate = Objects.requireNonNull(EditProfileFragmentTiLBirthDate.getEditText()).getText().toString().trim();

        String phone = Objects.requireNonNull(EditProfileFragmentTiLPhone.getEditText()).getText().toString().trim();
        String lastBloodDonation = Objects.requireNonNull(EditProfileFragmentTiLLastBloodDonation.getEditText()).getText().toString();
        String password = Objects.requireNonNull(EditProfileFragmentTiLPassword.getEditText()).getText().toString().trim();
        String rePassword = Objects.requireNonNull(EditProfileFragmentTiLRePassword.getEditText()).getText().toString().trim();

        if (!UserInputValidation.isValidName(name)) {
            EditProfileFragmentTiLName.setError("Please Enter Correct Name..");
        } else if (!UserInputValidation.isValidMail(email)) {
            EditProfileFragmentTiLEmail.setError("Please Enter Correct Email..");
        } else if (birthDate.isEmpty()) {
            EditProfileFragmentTiLBirthDate.setError("Please Enter Your Birth Date..");
        } else if (lastBloodDonation.isEmpty()) {
            EditProfileFragmentTiLLastBloodDonation.setError("Please Enter Last Blood Donation..");
        } else if (!UserInputValidation.isValidMobile(phone)) {
            EditProfileFragmentTiLPhone.setError("Please Enter Correct Phone Number..");
        } else if (!UserInputValidation.isValidPassword(password)) {
            EditProfileFragmentTiLPassword.setError("Please Enter Strong Password..");
        } else if (!UserInputValidation.isValidRePassword(rePassword, password)) {
            EditProfileFragmentTiLRePassword.setError("Re Password Is not equal Password..");
        } else {
            editUserProfileData(name, email, birthDate, cityId, phone, lastBloodDonation, password, rePassword, bloodType, apiToken);
        }

    }

    /**
     * Edit User Profile Information Using Api Call
     *
     * @param name
     * @param email
     * @param birthDate
     * @param cityId
     * @param phone
     * @param lastBloodDonation
     * @param password
     * @param rePassword
     * @param bloodType
     * @param apiToken
     */
    private void editUserProfileData(String name, String email, String birthDate, int cityId,
                                     String phone, String lastBloodDonation, String password, String rePassword,
                                     int bloodType, String apiToken) {
        Call<EditProfileData> editProfileDataCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .editProfileData(
                        name, email, birthDate, cityId, phone, lastBloodDonation, password, rePassword, bloodType, apiToken
                );

        editProfileDataCall.enqueue(new Callback<EditProfileData>() {
            @Override
            public void onResponse(Call<EditProfileData> call, Response<EditProfileData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        // Save User Government ID in SharedPreferences
                        getInstance(getContext()).setSelectedGovernmentItemPosition(
                                Integer.valueOf(response.body().getData().getUser().getCity().getGovernorateId())
                        );

                        // Save User City ID in SharedPreferences
                        getInstance(getContext()).setSelectedCityItemPosition(
                                Integer.valueOf(response.body().getData().getUser().getCityId())
                        );

                        // Save User Blood Type in SharedPreferences
                        getInstance(getContext()).setSelectedBloodItemPosition(
                                Integer.valueOf(response.body().getData().getUser().getBloodTypeId())
                        );

                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditProfileData> call, Throwable t) {

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
                        if (EditProfileFragmentSpinnerCities != null) {
                            EditProfileFragmentSpinnerCities.setAdapter(citiesAdapter);
                            EditProfileFragmentSpinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    mCityItemPosition = EditProfileFragmentSpinnerCities.getSelectedItemPosition();
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
        if (mCityItemPosition != -1 && mBloodTypeItemPosition != -1) {
            extractInputChickValidation(
                    mCityItemPosition, mBloodTypeItemPosition, SharedPrefManager.getInstance(getContext()).getApiToken()
            );
        }
    }
}
