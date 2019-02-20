package com.example.mm.bank.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.fragments.userCycle.LoginFragment;

public class UserCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);

        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replaceFragments(loginFragment,
                getSupportFragmentManager(),
                R.id.User_Cycle_FL_Fragment_Container,
                null,
                null);

    }
}
