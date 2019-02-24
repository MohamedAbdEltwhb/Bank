package com.example.mm.bank.ui.fragments.userCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.login.Login;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.UserInputValidation;
import com.example.mm.bank.ui.activities.HomeCycleActivity;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    private void extractInputChickValidation(){
        String phone = LoginFragmentTiLPhone.getEditText().getText().toString().trim();
        String password = LoginFragmentTiLPassword.getEditText().getText().toString().trim();

        if (!UserInputValidation.isValidMobile(phone)) {
            LoginFragmentTiLPhone.setError("Please Enter Correct Phone Number..");
        } else if (!UserInputValidation.isValidPassword(password)) {
            LoginFragmentTiLPassword.setError("Please Enter Strong Password..");
        }else {
            //doLoginUser(phone, password);
        }
    }

//    private void doLoginUser(String phone, String password) {
//
//        Call<Login> loginCall = RetrofitClient
//                .getInstance()
//                .getApiServices()
//                .doUserLogin(phone, password);
//
//        loginCall.enqueue(new Callback<Login>() {
//            @Override
//            public void onResponse(Call<Login> call, Response<Login> response) {
//                Login login = response.body();
//
//                if (response.isSuccessful()){
//
//                    Toast.makeText(getActivity(), "welcome", Toast.LENGTH_SHORT).show();
//
////                    SharedPrefManager.getInstance(getActivity()).saveUser(login.getLoginData().getClient());
////
////                    Intent toHome = new Intent(getActivity(), HomeCycleActivity.class);
////                    toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    toHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    getActivity().startActivity(toHome);
////                    getActivity().finish();
//
//                }else {
//                    Toast.makeText(getActivity(), "no", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Login> call, Throwable t) {
//
//            }
//        });
//    }

    @Override
    public void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(getContext()).isLoggedIn()){
            Intent toHome = new Intent(getActivity(), HomeCycleActivity.class);
            toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            toHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(toHome);
            getActivity().finish();
        }
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

//                HelperMethod.replaceFragments(
//                        new HomeFragment(),
//                        getActivity().getSupportFragmentManager(),
//                        R.id.User_Cycle_FL_Fragment_Container,
//                        null,
//                        null);
                break;

            case R.id.Login_Fragment_btn_Create_new_account:
                HelperMethod.replaceFragments(
                        new RegisterFragment(),
                        getActivity().getSupportFragmentManager(),
                        R.id.User_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;

            case R.id.Login_Fragment_Tv_forget_password:
                HelperMethod.replaceFragments(
                        new ForgetPasswordStep1Fragment(),
                        getActivity().getSupportFragmentManager(),
                        R.id.User_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;
        }
    }


}
