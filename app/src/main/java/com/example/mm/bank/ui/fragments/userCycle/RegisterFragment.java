package com.example.mm.bank.ui.fragments.userCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Unbinder unbinder;


    @BindView(R.id.Register_Fragment_Spinner_Cities)
    Spinner RegisterFragmentSpinnerCities;
    @BindView(R.id.Register_Fragment_Spinner_Blood_Type)
    Spinner RegisterFragmentSpinnerBloodType;


    private Toolbar mToolbar;
    private TextView mToolbarText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mToolbar = view.findViewById(R.id.Register_Page_toolbar);
        mToolbarText = mToolbar.findViewById(R.id.toolbar_text_title);

        mToolbarText.setText(getResources().getString(R.string.register_toolbar_title));

        unbinder = ButterKnife.bind(this, view);

        chickSpinner(RegisterFragmentSpinnerBloodType,
                HelperMethod.setSpinnerBloodType(getContext(), RegisterFragmentSpinnerBloodType));

        chickSpinner(RegisterFragmentSpinnerCities,
                HelperMethod.setSpinnerCities(getContext(), RegisterFragmentSpinnerCities));
        return view;
    }

    private void chickSpinner(Spinner spinner, Boolean b) {
        if (b) {
            spinner.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CustomSpinnerItem item = (CustomSpinnerItem) parent.getSelectedItem();
        Toast.makeText(getContext(), item.getSpinnerItemName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Register_fragment_Sign_up_btn)
    public void onViewClicked() {

        getActivity().startActivity(new Intent(getActivity(), HomeCycleActivity.class));
    }
}
