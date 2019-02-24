package com.example.mm.bank.ui.fragments.homeCycle.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.login.Client;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Unbinder unbinder;
    @BindView(R.id.order_customSpinner_Blood_type)
    Spinner orderCustomSpinnerBloodType;
    @BindView(R.id.order_customSpinner_Cities)
    Spinner orderCustomSpinnerCities;
    @BindView(R.id.txt)
    TextView txt;


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);

        Client client = SharedPrefManager.getInstance(getContext()).getUser();
        txt.setText("Welcome Back" + client.getName());

        if (HelperMethod.setSpinnerBloodType(getActivity(), orderCustomSpinnerBloodType)) {
            orderCustomSpinnerBloodType.setOnItemSelectedListener(this);
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

    @OnClick(R.id.Posts_Fragment_FAB)
    public void onViewClicked() {
        HelperMethod.replaceFragments(
                new NewRequestFragment(),
                getActivity().getSupportFragmentManager(),
                R.id.Home_Cycle_FL_Fragment_Container,
                null,
                null);
    }
}
