package com.example.mm.bank.ui.fragments.userCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.data.model.resetone.ResetPassword;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.UserInputValidation;

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
public class ForgetPasswordStep1Fragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.Forget_password1_Fragment_TiL_phone)
    TextInputLayout ForgetPassword1FragmentTiLPhone;

    public ForgetPasswordStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void extractInputChickValidation() {
        String phone = ForgetPassword1FragmentTiLPhone.getEditText().getText().toString();
        if (!UserInputValidation.isValidMobile(phone)) {
            ForgetPassword1FragmentTiLPhone.setError("Please Enter Correct Phone Number..");
        } else {
            resetEmile(phone);
        }
    }

    private void resetEmile(String phone) {

        Call<ResetPassword> resetPasswordCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .resetPasswordStep1(phone);

        resetPasswordCall.enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        HelperMethod.replaceFragments(
                                new ForgetPasswordStep2Fragment(),
                                getActivity().getSupportFragmentManager(),
                                R.id.User_Cycle_FL_Fragment_Container,
                                null,
                                null);

                    }
                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Forget_password1_Fragment_button_Send)
    public void onViewClicked() {
        extractInputChickValidation();

    }


}
