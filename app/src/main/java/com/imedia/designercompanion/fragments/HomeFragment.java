package com.imedia.designercompanion.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.adapters.HomeFragViewPagerAdapter;


public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tab_layout;
    private FloatingActionButton fab;
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
        fab = view.findViewById(R.id.fab);

        setupActions();

        return view;
    }

    private void setupActions() {
        HomeFragViewPagerAdapter adapter = new HomeFragViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewPager);
    }
}