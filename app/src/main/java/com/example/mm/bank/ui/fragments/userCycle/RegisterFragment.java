package com.example.mm.bank.ui.fragments.userCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.UserInputValidation;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Unbinder unbinder;

    @BindView(R.id.Register_Fragment_Spinner_Cities)
    Spinner RegisterFragmentSpinnerCities;
    @BindView(R.id.Register_Fragment_Spinner_Blood_Type)
    Spinner RegisterFragmentSpinnerBloodType;

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


    private Toolbar mToolbar;
    private TextView mToolbarText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mToolbar = view.findViewById(R.id.Register_Page_toolbar);
        mToolbarText = mToolbar.findViewById(R.id.toolbar_text_title);

        mToolbarText.setText(getResources().getString(R.string.register_toolbar_title));

        unbinder = ButterKnife.bind(this, view);

        chickSpinner(RegisterFragmentSpinnerBloodType,
                HelperMethod.setSpinnerBloodType(getContext(), RegisterFragmentSpinnerBloodType));

        return view;
    }

    private void extractInputValidation() {
        String name = RegisterFragmentTiLName.getEditText().getText().toString();
        String email = RegisterFragmentTiLEmail.getEditText().getText().toString().trim();
        String birthDate = RegisterFragmentTiLBirthDate.getEditText().getText().toString().trim();
        String lastBloodDonation = RegisterFragmentTiLLastBloodDonation.getEditText().getText().toString();
        String phone = RegisterFragmentTiLPhone.getEditText().getText().toString().trim();
        String password = RegisterFragmentTiLPassword.getEditText().getText().toString().trim();
        String rePassword = RegisterFragmentTiLRePassword.getEditText().getText().toString().trim();

        if (!UserInputValidation.isValidName(name)){
            RegisterFragmentTiLName.setError("Please Enter Correct Name..");
        } else if (!UserInputValidation.isValidMail(email)){
            RegisterFragmentTiLEmail.setError("Please Enter Correct Email..");
        } else if (birthDate.isEmpty()){
            RegisterFragmentTiLBirthDate.setError("Please Enter Your Birth Date..");
        } else if (lastBloodDonation.isEmpty()){
            RegisterFragmentTiLLastBloodDonation.setError("Please Enter Last Blood Donation..");
        } else if (!UserInputValidation.isValidMobile(phone)){
            RegisterFragmentTiLPhone.setError("Please Enter Correct Phone Number..");
        } else if (!UserInputValidation.isValidPassword(password)){
            RegisterFragmentTiLPassword.setError("Please Enter Strong Password..");
        } else if (!UserInputValidation.isValidRePassword(rePassword, password)){
            RegisterFragmentTiLRePassword.setError("Re Password Is not equal Password..");
        }else {

        }

    }

    private void chickSpinner(Spinner spinner, Boolean b) {
        if (b) {
            spinner.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CustomSpinnerItem item = (CustomSpinnerItem) parent.getSelectedItem();
        Toast.makeText(getContext(), item.getSpinnerItemName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Register_fragment_Sign_up_btn)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), HomeCycleActivity.class));
    }


}
