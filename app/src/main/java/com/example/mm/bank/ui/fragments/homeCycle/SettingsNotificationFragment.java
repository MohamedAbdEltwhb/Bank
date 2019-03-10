package com.example.mm.bank.ui.fragments.homeCycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsNotificationFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.CheckBox_A_plus)
    CheckBox CheckBoxAPlus;
    @BindView(R.id.CheckBox_A_minus)
    CheckBox CheckBoxAMinus;
    @BindView(R.id.CheckBox_O_plus)
    CheckBox CheckBoxOPlus;
    @BindView(R.id.CheckBox_O_minus)
    CheckBox CheckBoxOMinus;
    @BindView(R.id.CheckBox_AB_plus)
    CheckBox CheckBoxABPlus;
    @BindView(R.id.CheckBox_AB_minus)
    CheckBox CheckBoxABMinus;

    @BindView(R.id.Hurghada)
    CheckBox Hurghada;
    @BindView(R.id.Dakahlia)
    CheckBox Dakahlia;
    @BindView(R.id.Minya)
    CheckBox Minya;
    @BindView(R.id.Kafr_El_Sheikh)
    CheckBox KafrElSheikh;
    @BindView(R.id.Cairo)
    CheckBox Cairo;
    @BindView(R.id.Sharqia)
    CheckBox Sharqia;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Configure Back Pressed Listener Button */
        HelperMethod.onBackPressedListener(getContext(), getActivity());
    }

    public SettingsNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_notification, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.notificatoin_Settings_fragment_Save_btn)
    public void onViewClicked() {
    }
}
