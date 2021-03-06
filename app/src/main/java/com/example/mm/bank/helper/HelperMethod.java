package com.example.mm.bank.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.spinner.CustomSpinnerAdapter;
import com.example.mm.bank.adapter.spinner.SpinnerGovernmentsAdapter;
import com.example.mm.bank.data.model.blood_type.BloodDatum;
import com.example.mm.bank.data.model.blood_type.BloodType;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.governorates.GovernoratesData;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

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

    /**
     * Configure Back Pressed Listener Button
     */
    public static void onBackPressedListener(final Context context, final FragmentActivity fragmentActivity) {
        ((HomeCycleActivity) Objects.requireNonNull(context))
                .setOnBackPressedListener(new BackPressedListener(fragmentActivity) {
                    @Override
                    public void doBack() {
                        HelperMethod.replaceFragments(
                                new HomeFragment(),
                                fragmentActivity.getSupportFragmentManager(),
                                R.id.Home_Cycle_FL_Fragment_Container,
                                null,
                                null);
                    }
                });
    }

    public static boolean checkConnection(FragmentActivity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null
                && info.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    public void selectDate() {
        int mYear=0;
        int mMonth=0;
        int mDay= 0;
        TextView expiry = null;

        final TextView finalExpiry = expiry;
        final Context context = null;
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    final Calendar myCalendar = Calendar.getInstance();
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        finalExpiry.setText(sdf.format(myCalendar.getTime()));
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
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
     * Get Blood Types Using Api Call
     */
   public static void getSpinnerBloodTypes(final Context context, final Spinner spinner) {

       Call<BloodType> call = RetrofitClient
               .getInstance()
               .getApiServices()
               .getBloodTypes();

       call.enqueue(new Callback<BloodType>() {
           @Override
           public void onResponse(@NonNull Call<BloodType> call, @NonNull Response<BloodType> response) {

               if (response.isSuccessful()) {

                   if (response.body().getStatus() == 1){
                       CustomSpinnerAdapter Adapter = new CustomSpinnerAdapter(context, response.body().getData());
                       if (spinner != null) {
                           spinner.setAdapter(Adapter);
                           spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                               @Override
                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                   BloodDatum data = (BloodDatum) parent.getSelectedItem();
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
           public void onFailure(@NonNull Call<BloodType> call, @NonNull Throwable t) {

           }
       });
   }


}
