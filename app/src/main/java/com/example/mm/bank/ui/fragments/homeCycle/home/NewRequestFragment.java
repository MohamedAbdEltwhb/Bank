package com.example.mm.bank.ui.fragments.homeCycle.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.New_request_F_Blood_Type)
    Spinner NewRequestFTiLBloodType;
    @BindView(R.id.New_request_F_Spinner_Cities)
    Spinner NewRequestFSpinnerCities;
    @BindView(R.id.New_request_F_Spinner_Governments)
    Spinner NewRequestFSpinnerGovernments;

    private String mBloodType = null;
    private String mCitiesId = null;

    public NewRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_request, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Set Blood Type to Spinner*/
        HelperMethod.setSpinnerBloodType(getContext(), NewRequestFTiLBloodType);

        /* Get Governments Using Api Call*/
        HelperMethod.getGovernments(getContext(), NewRequestFSpinnerGovernments);

        /* Get Cities Using Api Call*/
        mCitiesId = HelperMethod.getCities(getContext(), NewRequestFSpinnerCities);

        NewRequestFTiLBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerItem item = (CustomSpinnerItem) parent.getSelectedItem();
                mBloodType = item.getSpinnerItemName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
