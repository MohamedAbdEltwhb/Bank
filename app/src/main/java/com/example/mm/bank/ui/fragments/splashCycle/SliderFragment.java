package com.example.mm.bank.ui.fragments.splashCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.SliderPagerAdapter;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.activities.UserCycleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.Slider_Fragment_viewPager)
    ViewPager SliderFragmentViewPager;
    @BindView(R.id.Slider_fragment_Indicator)
    CircleIndicator SliderFragmentIndicator;

    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);

        SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(getContext());
        SliderFragmentViewPager.setAdapter(sliderPagerAdapter);
        SliderFragmentIndicator.setViewPager(SliderFragmentViewPager);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Slider_Fragment_btn_skip)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), UserCycleActivity.class));
    }

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
}
