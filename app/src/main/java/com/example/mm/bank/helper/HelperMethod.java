package com.example.mm.bank.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.adapter.spinner.CustomSpinnerAdapter;
import com.example.mm.bank.adapter.spinner.SpinnerCitiesAdapter;
import com.example.mm.bank.adapter.spinner.SpinnerGovernmentsAdapter;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.cities.CitiesData;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.governorates.GovernoratesData;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.ui.custom.CustomSpinnerItemsData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperMethod {


    public static void replaceFragments(Fragment fragment, FragmentManager supportFragmentManager
            , int id, TextView toolBarTitle, String title) {

        supportFragmentManager.beginTransaction()
        .replace(id, fragment)
        .addToBackStack(null)
        .commit();
        if (toolBarTitle != null){
            toolBarTitle.setText(title);
        }
    }



    public static Boolean setSpinnerBloodType(Context context, Spinner spinner){
        CustomSpinnerAdapter Adapter = new CustomSpinnerAdapter(context,
                CustomSpinnerItemsData.getmCustomListBloodType());
        if (spinner != null) {
            spinner.setAdapter(Adapter);
            return true;
        }
        return false;
    }


    /**
     * Get Governments Using Api Call
     */
    public static void getGovernments(final Context context, final Spinner spinner) {

        Call<Governorates> call = RetrofitClient
                .getInstance()
                .getApiServices()
                .getGovernments();

        call.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(@NonNull Call<Governorates> call, @NonNull Response<Governorates> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 1){
                        SpinnerGovernmentsAdapter Adapter = new SpinnerGovernmentsAdapter(context, response.body().getData());
                        if (spinner != null) {
                            spinner.setAdapter(Adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    GovernoratesData data = (GovernoratesData) parent.getSelectedItem();
                                    Toast.makeText(context, data.getName(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Governorates> call, @NonNull Throwable t) {

            }
        });
    }


    /**
     * Get Cities Using Api Call
     */
    private static String citiesId;
    public static String getCities(final Context context, final Spinner spinner) {

        Call<Cities> citiesCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getCities(new CitiesData().getGovernorateId());

        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(@NonNull Call<Cities> call, @NonNull final Response<Cities> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1){
                        SpinnerCitiesAdapter citiesAdapter = new SpinnerCitiesAdapter(context, response.body().getData());
                        if (spinner != null) {
                            spinner.setAdapter(citiesAdapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    CitiesData data = (CitiesData) parent.getSelectedItem();
                                    citiesId = data.getId().toString();
                                    //Toast.makeText(context, citiesId, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Cities> call, @NonNull Throwable t) {

            }
        });
        return citiesId;
    }




}
