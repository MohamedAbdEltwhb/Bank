package com.example.mm.bank.ui.fragments.userCycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.model.user.reset_password.newPassword.NewPassword;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.utils.UserInputValidation;
import com.example.mm.bank.ui.activities.UserCycleActivity;

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
public class ForgetPasswordStep2Fragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.Forget_password2_Fragment_TiL_code)
    TextInputLayout ForgetPassword2FragmentTiLCode;
    @BindView(R.id.Forget_password2_Fragment_TiL_Password)
    TextInputLayout ForgetPassword2FragmentTiLPassword;
    @BindView(R.id.Forget_password2_Fragment_TiL_Re_Password)
    TextInputLayout ForgetPassword2FragmentTiLRePassword;
    @BindView(R.id.Forget_password2_Fragment_TiL_phone)
    TextInputLayout ForgetPassword2FragmentTiLPhone;

    public ForgetPasswordStep2Fragment() {
        // Required empty public constructor
    }

    /**
     * Configure Back Pressed Listener Button
     */
    public void configureBackPressedListener(){
        ((UserCycleActivity) Objects.requireNonNull(getActivity()))
                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
            @Override
            public void doBack(){
                HelperMethod.replaceFragments(
                        new ForgetPasswordStep1Fragment(),
                        getActivity().getSupportFragmentManager(),
                        R.id.User_Cycle_FL_Fragment_Container,
                        null,
                        null);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureBackPressedListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Extract Input >> Code & Password & Re Password & Phone
     */
    private void extractInputChickValidation() {
        String code = ForgetPassword2FragmentTiLCode.getEditText().getText().toString();
        String password = ForgetPassword2FragmentTiLPassword.getEditText().getText().toString();
        String re_password = ForgetPassword2FragmentTiLRePassword.getEditText().getText().toString();
        String phone = ForgetPassword2FragmentTiLPhone.getEditText().getText().toString();

        if (!UserInputValidation.isValidPassword(password)) {
            ForgetPassword2FragmentTiLPassword.setError("Please Enter Strong Password..");
        } else if (!UserInputValidation.isValidRePassword(re_password, password)) {
            ForgetPassword2FragmentTiLRePassword.setError("Re Password Is not equal Password..");
        }
        if (!UserInputValidation.isValidMobile(phone)) {
            ForgetPassword2FragmentTiLPhone.setError("Please Enter Correct Phone Number..");
        } else {

            /*Do User Reset Password Method Using Api Call*/
            resetPassword(password, re_password, code, phone);
        }
    }

    /**
     * Do User Reset Password Using Api Call
     *
     * @param password
     * @param re_password
     * @param code
     * @param phone
     */
    private void resetPassword(String password, String re_password, String code, String phone) {

        Call<NewPassword> newPasswordCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .addNewPassword(password, re_password, code, phone);

        newPasswordCall.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        HelperMethod.replaceFragments(
                                new LoginFragment(),
                                getActivity().getSupportFragmentManager(),
                                R.id.User_Cycle_FL_Fragment_Container,
                                null,
                                null);

                    } else {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Forget_password2_Fragment_button_Change_Password)
    public void onViewClicked() {
        extractInputChickValidation();
    }

}
