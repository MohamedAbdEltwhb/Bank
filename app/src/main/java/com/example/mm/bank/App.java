package com.example.mm.bank;

import android.app.Application;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

import java.util.Locale;

public class App extends Application {

    private static Application mInstance;
    public static String Lang = "en";
    Locale loc;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Lang.equals("en")) {
            Lang = "en";
            String ar = "en";
            loc = new Locale(ar);
            Locale.setDefault(loc);
            Configuration config = new Configuration();
            config.locale = loc;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (Lang.equals("en")) {
            loc = new Locale("en");
        } else {
            loc = new Locale("ar");
        }

        Locale.setDefault(loc);
        Configuration config = new Configuration();
        config.locale = loc;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

    }

}
