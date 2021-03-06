package com.example.mm.bank.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.fragments.splashCycle.SplashFragment;

public class SplashCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent));


        HelperMethod.replaceFragments(
                new SplashFragment(),
                getSupportFragmentManager(),
                R.id.Splash_Cycle_FL_Fragment_Container,
                null,
                null
        );
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
