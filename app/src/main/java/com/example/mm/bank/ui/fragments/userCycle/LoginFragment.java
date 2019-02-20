package com.example.mm.bank.ui.fragments.userCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Unbinder unbinder;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Login_Fragment_btn_Login, R.id.Login_Fragment_btn_Create_new_account, R.id.Login_Fragment_Tv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Login_Fragment_btn_Login:

                HelperMethod.replaceFragments(
                        new HomeFragment(),
                        getActivity().getSupportFragmentManager(),
                        R.id.User_Cycle_FL_Fragment_Container,
                        null,
                        null);
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
