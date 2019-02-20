package com.example.mm.bank.ui.fragments.splashCycle;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.jaeger.library.StatusBarUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private final long SPLASH_DISPLAY_LENGTH = 2000;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        //StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.transparentColor));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SliderFragment sliderFragment = new SliderFragment();
                HelperMethod.replaceFragments(
                        sliderFragment,
                        getActivity().getSupportFragmentManager(),
                        R.id.Splash_Cycle_FL_Fragment_Container,
                        null,
                        null
                );
            }
        },SPLASH_DISPLAY_LENGTH);

        return view;
    }


}
