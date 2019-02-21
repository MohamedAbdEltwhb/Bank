package com.example.mm.bank.ui.fragments.homeCycle.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.New_request_F_Blood_Type)
    Spinner NewRequestFTiLBloodType;

    Unbinder unbinder;
    @BindView(R.id.New_request_F_Spinner_Cities)
    Spinner NewRequestFSpinnerCities;

    public NewRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new_request, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (HelperMethod.setSpinnerBloodType(getContext(), NewRequestFTiLBloodType)) {
            NewRequestFTiLBloodType.setOnItemSelectedListener(this);
        }



        return view;
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
}
