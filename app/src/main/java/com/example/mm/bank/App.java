package com.example.mm.bank;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

import com.example.mm.bank.helper.server.NetworkStateChangeReceiver;

import java.util.Locale;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class App extends Application {

    private static final String WIFI_STATE_CHANGE_ACTION = "android.net.wifi.WIFI_STATE_CHANGED";

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

        registerForNetworkChangeEvents(this);

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

    public static void registerForNetworkChangeEvents(final Context context) {
        NetworkStateChangeReceiver networkStateChangeReceiver = new NetworkStateChangeReceiver();
        context.registerReceiver(networkStateChangeReceiver, new IntentFilter(CONNECTIVITY_ACTION));
        context.registerReceiver(networkStateChangeReceiver, new IntentFilter(WIFI_STATE_CHANGE_ACTION));
    }

}
