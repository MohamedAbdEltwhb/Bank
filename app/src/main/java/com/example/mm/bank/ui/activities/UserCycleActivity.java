package com.example.mm.bank.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mm.bank.helper.OnBackPressedListener;
import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.fragments.userCycle.LoginFragment;

public class UserCycleActivity extends AppCompatActivity {

    protected OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);

        HelperMethod.replaceFragments(
                new LoginFragment(),
                getSupportFragmentManager(),
                R.id.User_Cycle_FL_Fragment_Container,
                null,
                null);

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        } else {
            super.onBackPressed();
        }
    }


}
