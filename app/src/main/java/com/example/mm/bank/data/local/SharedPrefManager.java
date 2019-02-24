package com.example.mm.bank.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mm.bank.data.model.login.Client;

import static com.example.mm.bank.helper.Constant.SHARED_PREF_NAME;

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

    public void saveUser(Client client){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", client.getId());
        editor.putString("createdAt", client.getCreatedAt());
        editor.putString("updatedAt", client.getUpdatedAt());
        editor.putString("name", client.getName());
        editor.putString("email", client.getEmail());
        editor.putString("birthDate", client.getBirthDate());
        editor.putString("cityId", client.getCityId());
        editor.putString("phone", client.getPhone());
        editor.putString("donationLastDate", client.getDonationLastDate());
        editor.putString("bloodType", client.getBloodType());
        editor.putString("isActive", client.getIsActive());
        editor.putString("pinCode", client.getPinCode());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public Client getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new Client(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("createdAt", null),
                sharedPreferences.getString("updatedAt", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("birthDate", null),
                sharedPreferences.getString("cityId", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("donationLastDate", null),
                sharedPreferences.getString("bloodType", null),
                sharedPreferences.getString("isActive", null),
                sharedPreferences.getString("pinCode", null));
    }

    public void clare(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
