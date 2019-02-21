package com.example.mm.bank.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mm.bank.adapter.spinner.CustomSpinnerAdapter;
import com.example.mm.bank.ui.custom.CustomSpinnerItemsData;

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

}
