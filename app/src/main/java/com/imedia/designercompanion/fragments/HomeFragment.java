package com.imedia.designercompanion.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.imagepickerlibrary.ImagePickerBottomsheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.adapters.HomeFragViewPagerAdapter;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;


public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout tab_layout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        tab_layout = view.findViewById(R.id.tab_layout);


        setupActions();

        return view;
    }

    private void setupActions() {
        HomeFragViewPagerAdapter adapter = new HomeFragViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewPager);


    }


}