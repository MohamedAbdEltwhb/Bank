package com.example.mm.bank.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mm.bank.R;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.fragments.homeCycle.AboutApplicationFragment;
import com.example.mm.bank.ui.fragments.homeCycle.ContactWithUsFragment;
import com.example.mm.bank.ui.fragments.homeCycle.EditProfileFragment;
import com.example.mm.bank.ui.fragments.homeCycle.FavoriteFragment;
import com.example.mm.bank.ui.fragments.homeCycle.NotificationFragment;
import com.example.mm.bank.ui.fragments.homeCycle.SettingsNotificationFragment;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;
import com.example.mm.bank.ui.fragments.userCycle.RegisterFragment;

public class HomeCycleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

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

        switch (id){
            case R.id.nav_profile:
                HelperMethod.replaceFragments(
                        new EditProfileFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;

            case R.id.nav_notification:
                HelperMethod.replaceFragments(
                        new SettingsNotificationFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;

            case R.id.nav_favorites:
                HelperMethod.replaceFragments(
                        new FavoriteFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;
            case R.id.nav_home:
                HelperMethod.replaceFragments(
                        new HomeFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;
            case R.id.nav_instructions:
                break;
            case R.id.nav_contact:
                HelperMethod.replaceFragments(
                        new ContactWithUsFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;
            case R.id.nav_about_application:
                HelperMethod.replaceFragments(
                        new AboutApplicationFragment(),
                        getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;
            case R.id.nav_evaluation:
                break;
            case R.id.nav_log_out:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void addNewNavFragments(){
//        HelperMethod.replaceFragments(
//                new AboutApplicationFragment(),
//                getSupportFragmentManager(),
//                R.id.Home_Cycle_FL_Fragment_Container,
//                null,
//                null);
//    }
}
