package com.imedia.designercompanion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.imedia.designercompanion.BuildConfig;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.fragments.ConnectOthersFragment;
import com.imedia.designercompanion.fragments.ContactUsFragment;
import com.imedia.designercompanion.fragments.CustomersFragment;
import com.imedia.designercompanion.fragments.HomeFragment;
import com.imedia.designercompanion.fragments.MyProfileFragment;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ViewPager viewpager;
    private LinearLayout logoutBTN;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setupViewItems();

        Fragment fragClas = new HomeFragment();
        changeFragment(fragClas);
    }

    @Override

    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.

        toggle.syncState();
    }

    @Override

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles
        toggle.onConfigurationChanged(newConfig);

    }

    private void setupViewItems() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        toggle.setHomeAsUpIndicator(R.drawable.ic_hamburger);

        navigationView = findViewById(R.id.nvView);

        //Logout BTN
        logoutBTN = navigationView.findViewById(R.id.logout_BTN);

        setActionOnViewItems();
    }

    private void setActionOnViewItems(){

        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                Class fragClas = null;

                switch (id) {

                    case R.id.home_menu:
                        fragClas = HomeFragment.class;
                        break;
                    case R.id.customers_menu:
                        fragClas = CustomersFragment.class;
                        break;
                    case R.id.connect_others_menu:
                        fragClas = ConnectOthersFragment.class;
                        break;
                    case R.id.contact_us_menu:
                        fragClas = ContactUsFragment.class;
                        break;
                    case R.id.profile_menu:
                        fragClas = MyProfileFragment.class;
                        break;
                    case R.id.share_menu:
                        fragClas = null;
                    default:
                        //return true;
                }

                try {
                    fragment = (Fragment) fragClas.newInstance();
                } catch (Exception e) {

                    e.printStackTrace();

                }

                changeFragment(fragment);

                item.setChecked(true);

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });

    }

    private void changeFragment(Fragment fragment) {

        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag((fragmentTag)) == null) {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(fragment.getTag()).commit();
        }
        //get fragment transaction
       // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //set new fragment in fragment_container (FrameLayout)
//        fragmentTransaction.replace(R.id.flContent, fragment);
//        fragmentTransaction.commit();
        // fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }

    private void logoutUser() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout")
                .setMessage("You are about to logout from Designer Companion")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton("logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                        finish();
                    }
                }).show();

    }

    public void shareApp(MenuItem item) {

        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Color Interactive Game");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "share via"));
        } catch (Exception e) {
            //e.toString();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1){
                logoutUser();
            }else {
                super.onBackPressed();
            }
        }

    }

}