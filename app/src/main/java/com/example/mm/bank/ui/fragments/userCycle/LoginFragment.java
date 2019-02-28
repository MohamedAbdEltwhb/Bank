package com.example.mm.bank.ui.fragments.userCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.model.regester.Register;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.UserInputValidation;
import com.example.mm.bank.ui.activities.HomeCycleActivity;

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
 */
public class LoginFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.Login_Fragment_TiL_Phone)
    TextInputLayout LoginFragmentTiLPhone;
    @BindView(R.id.Login_Fragment_TiL_Password)
    TextInputLayout LoginFragmentTiLPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    private void extractInputChickValidation() {
        String phone = Objects.requireNonNull(LoginFragmentTiLPhone.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(LoginFragmentTiLPassword.getEditText()).getText().toString().trim();

        if (!UserInputValidation.isValidMobile(phone)) {
            LoginFragmentTiLPhone.setError("Please Enter Correct Phone Number..");
        } else if (!UserInputValidation.isValidPassword(password)) {
            LoginFragmentTiLPassword.setError("Please Enter Strong Password..");
        } else {
            doLoginUser(phone, password);
        }
    }

    /**
     * Do User Login Using Api Call
     *
     * @param phone
     * @param password
     */
    private void doLoginUser(String phone, String password) {

        Call<Register> loginCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .doUserLogin(phone, password);

        loginCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(@NonNull Call<Register> call, @NonNull Response<Register> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        // Save All User PostsDetailsData in SharedPreferences
                        getInstance(getContext()).saveUser(response.body().getRegisterData().getRegisterClient());

                        // Save User ApiToken in SharedPreferences
                        getInstance(getContext()).setApiToken(response.body().getRegisterData().getApiToken());

                        Intent toHome = new Intent(getActivity(), HomeCycleActivity.class);
                        toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        toHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Objects.requireNonNull(getActivity()).startActivity(toHome);
                        getActivity().finish();


                    } else {
                        if (response.body() != null) {
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Register> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Login_Fragment_btn_Login, R.id.Login_Fragment_btn_Create_new_account, R.id.Login_Fragment_Tv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Login_Fragment_btn_Login:
                extractInputChickValidation();
                break;

            case R.id.Login_Fragment_btn_Create_new_account:
                HelperMethod.replaceFragments(
                        new RegisterFragment(),
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                        R.id.User_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;

            case R.id.Login_Fragment_Tv_forget_password:
                HelperMethod.replaceFragments(
                        new ForgetPasswordStep1Fragment(),
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                        R.id.User_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;
        }
    }


}
