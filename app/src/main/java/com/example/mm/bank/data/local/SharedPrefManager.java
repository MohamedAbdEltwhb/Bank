package com.example.mm.bank.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mm.bank.data.model.user.regester.RegisterClient;

import static com.example.mm.bank.helper.Constant.API_TOKEN;
import static com.example.mm.bank.helper.Constant.BIRTH_DATE;
import static com.example.mm.bank.helper.Constant.BLOOD_TYPE;
import static com.example.mm.bank.helper.Constant.CITY_ID;
import static com.example.mm.bank.helper.Constant.CREATED_AT;
import static com.example.mm.bank.helper.Constant.DONATION_LAST_DATE;
import static com.example.mm.bank.helper.Constant.EMILE;
import static com.example.mm.bank.helper.Constant.ID;
import static com.example.mm.bank.helper.Constant.IS_ACTIVE;
import static com.example.mm.bank.helper.Constant.NAME;
import static com.example.mm.bank.helper.Constant.PHONE;
import static com.example.mm.bank.helper.Constant.PIN_CODE;
import static com.example.mm.bank.helper.Constant.SHARED_PREF_NAME;
import static com.example.mm.bank.helper.Constant.UPDATED_AT;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private Context mContext;

    private SharedPrefManager(Context context) {
        this.mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUser(RegisterClient registerClient){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ID, registerClient.getId());
        editor.putString(CREATED_AT, registerClient.getCreatedAt());
        editor.putString(UPDATED_AT, registerClient.getUpdatedAt());
        editor.putString(NAME, registerClient.getName());
        editor.putString(EMILE, registerClient.getEmail());
        editor.putString(BIRTH_DATE, registerClient.getBirthDate());
        editor.putString(CITY_ID, registerClient.getCityId());
        editor.putString(PHONE, registerClient.getPhone());
        editor.putString(DONATION_LAST_DATE, registerClient.getDonationLastDate());
        editor.putString(BLOOD_TYPE, registerClient.getBloodType());
        editor.putString(IS_ACTIVE, registerClient.getIsActive());
        editor.putString(PIN_CODE, registerClient.getPinCode());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ID, -1) != -1;
    }

    public RegisterClient getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new RegisterClient(
                sharedPreferences.getInt(ID, -1),
                sharedPreferences.getString(CREATED_AT, null),
                sharedPreferences.getString(UPDATED_AT, null),
                sharedPreferences.getString(NAME, null),
                sharedPreferences.getString(EMILE, null),
                sharedPreferences.getString(BIRTH_DATE, null),
                sharedPreferences.getString(CITY_ID, null),
                sharedPreferences.getString(PHONE, null),
                sharedPreferences.getString(DONATION_LAST_DATE, null),
                sharedPreferences.getString(BLOOD_TYPE, null),
                sharedPreferences.getString(IS_ACTIVE, null),
                sharedPreferences.getString(PIN_CODE, null));
    }

    public void setApiToken(String apiToken){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(API_TOKEN, apiToken);
        editor.apply();
    }

    public String getApiToken(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(API_TOKEN,"");
    }

    public void clare(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
