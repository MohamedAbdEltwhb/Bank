package com.example.mm.bank.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mm.bank.ui.custom.CustomSpinnerAdapter;
import com.example.mm.bank.ui.custom.CustomSpinnerItem;
import com.example.mm.bank.ui.custom.CustomSpinnerItemsData;

import java.util.ArrayList;

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

    public static Boolean setSpinnerCities(Context cities, Spinner spinner){
        CustomSpinnerAdapter Adapter = new CustomSpinnerAdapter(cities
                , CustomSpinnerItemsData.getmCustomListCities());
        if (spinner != null) {
            spinner.setAdapter(Adapter);
            return true;
        }
        return false;
    }

    public static Boolean setSpinnerBloodType(Context cities, Spinner spinner){
        CustomSpinnerAdapter Adapter = new CustomSpinnerAdapter(cities,
                CustomSpinnerItemsData.getmCustomListBloodType());
        if (spinner != null) {
            spinner.setAdapter(Adapter);
            return true;
        }
        return false;
    }

}
