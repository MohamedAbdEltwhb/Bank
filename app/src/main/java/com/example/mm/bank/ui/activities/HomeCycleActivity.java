package com.example.mm.bank.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.OnBackPressedListener;
import com.example.mm.bank.helper.server.NetworkStateChangeReceiver;
import com.example.mm.bank.ui.custom.CustomDialog;
import com.example.mm.bank.ui.fragments.homeCycle.AboutApplicationFragment;
import com.example.mm.bank.ui.fragments.homeCycle.ContactWithUsFragment;
import com.example.mm.bank.ui.fragments.homeCycle.NotificationFragment;
import com.example.mm.bank.ui.fragments.homeCycle.SettingsNotificationFragment;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;
import com.example.mm.bank.ui.fragments.homeCycle.order.OrderRequestInformationActivity;
import com.example.mm.bank.ui.fragments.homeCycle.order.SendDonationDetails;
import com.example.mm.bank.ui.fragments.homeCycle.posts.FavoriteFragment;
import com.example.mm.bank.ui.fragments.homeCycle.posts.OnItemPostDetailsSend;
import com.example.mm.bank.ui.fragments.homeCycle.posts.PostsDetailsFragment;
import com.example.mm.bank.ui.fragments.userCycle.EditProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mm.bank.helper.server.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class HomeCycleActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        OnItemPostDetailsSend,
        SendDonationDetails {

    protected OnBackPressedListener onBackPressedListener;
    @BindView(R.id.TextView_ToolPar_title)
    TextView TextViewToolParTitle;

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //__________________________________________________________________________________________________

        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);

                String networkStatus = isNetworkAvailable ? "connected" : "disconnected";
                Snackbar.make(findViewById(R.id.activity_Home), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
                //new CustomDialog(HomeCycleActivity.this).showCustomDialog();

//                if (isNetworkAvailable){
//                    Snackbar.make(findViewById(R.id.activity_Home), "Network Connection is Pack ..", Snackbar.LENGTH_LONG).show();
//                }else {
//                    new CustomDialog(HomeCycleActivity.this).showCustomDialog();
//                }
            }
        }, intentFilter);

        //________________________________________________________________________________________________________________

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //toggle.setHomeAsUpIndicator(R.drawable.ic_nav_icon);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_icon);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HelperMethod.replaceFragments(
                new HomeFragment(),
                getSupportFragmentManager(),
                R.id.Home_Cycle_FL_Fragment_Container,
                null,
                null);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (onBackPressedListener != null) {
            onBackPressedListener.doBack();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_cycle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification_manu) {
            HelperMethod.replaceFragments(
                    new NotificationFragment(),
                    getSupportFragmentManager(),
                    R.id.Home_Cycle_FL_Fragment_Container,
                    null,
                    null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profile:
                HelperMethod.replaceFragments(
                        new EditProfileFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        TextViewToolParTitle,
                        "Edit Profile");
                break;

            case R.id.nav_notification:
                HelperMethod.replaceFragments(
                        new SettingsNotificationFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        TextViewToolParTitle,
                        "Notification");
                break;

            case R.id.nav_favorites:
                HelperMethod.replaceFragments(
                        new FavoriteFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        TextViewToolParTitle,
                        "Favorites");
                break;
            case R.id.nav_home:
                HelperMethod.replaceFragments(
                        new HomeFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        TextViewToolParTitle,
                        "Home");
                break;
            case R.id.nav_instructions:
                break;
            case R.id.nav_contact:
                HelperMethod.replaceFragments(
                        new ContactWithUsFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        TextViewToolParTitle,
                        "Contact Us");
                break;
            case R.id.nav_about_application:
                HelperMethod.replaceFragments(
                        new AboutApplicationFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        TextViewToolParTitle,
                        "About Application");
                break;
            case R.id.nav_evaluation:
                break;
            case R.id.nav_log_out:
                logoutUser();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        SharedPrefManager.getInstance(this).clare();

        Intent toLogin = new Intent(this, UserCycleActivity.class);
        toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toLogin);
        finish();
    }


    @Override
    public void onSentItemDetails(int id) {
        PostsDetailsFragment detailsFragment = new PostsDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(POST_ID, id);
        detailsFragment.setArguments(bundle);

        HelperMethod.replaceFragments(detailsFragment,
                getSupportFragmentManager(),
                R.id.Home_Cycle_FL_Fragment_Container,
                null,
                null);
    }

    @Override
    public void setDonationDetails(Integer id) {
        Intent intent = new Intent(this, OrderRequestInformationActivity.class);
        intent.putExtra(CLINT_ID, id);
        startActivity(intent);


//        OrderRequestInformationFragment requestInformationFragment = new OrderRequestInformationFragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putString(CLINT_ID, id);
//        requestInformationFragment.setArguments(bundle);
//
//        HelperMethod.replaceFragments(
//                requestInformationFragment,
//                getSupportFragmentManager(),
//                R.id.Home_Cycle_FL_Fragment_Container,
//                null,
//                null);
    }
}
