package com.example.mm.bank.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mm.bank.data.model.regester.RegisterClient;

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

    public void saveUser(RegisterClient registerClient){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", registerClient.getId());
        editor.putString("createdAt", registerClient.getCreatedAt());
        editor.putString("updatedAt", registerClient.getUpdatedAt());
        editor.putString("name", registerClient.getName());
        editor.putString("email", registerClient.getEmail());
        editor.putString("birthDate", registerClient.getBirthDate());
        editor.putString("cityId", registerClient.getCityId());
        editor.putString("phone", registerClient.getPhone());
        editor.putString("donationLastDate", registerClient.getDonationLastDate());
        editor.putString("bloodType", registerClient.getBloodType());
        editor.putString("isActive", registerClient.getIsActive());
        editor.putString("pinCode", registerClient.getPinCode());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public RegisterClient getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new RegisterClient(
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

    public void setApiToken(String apiToken){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("api_token", apiToken);
        editor.apply();
    }

    public String getApiToken(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String apiToken = sharedPreferences.getString("api_token","");
        return apiToken ;
    }

    public void clare(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
