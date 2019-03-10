package com.example.mm.bank.ui.fragments.splashCycle;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.jaeger.library.StatusBarUtil;

import static com.example.mm.bank.helper.Constant.SPLASH_DISPLAY_LENGTH;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.transparentColor));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
                    Intent toHome = new Intent(getActivity(), HomeCycleActivity.class);
                    toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(toHome);
                    getActivity().finish();

                }else {
                    HelperMethod.replaceFragments(
                            new SliderFragment(),
                            getActivity().getSupportFragmentManager(),
                            R.id.Splash_Cycle_FL_Fragment_Container,
                            null,
                            null
                    );
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

        return view;
    }


}
