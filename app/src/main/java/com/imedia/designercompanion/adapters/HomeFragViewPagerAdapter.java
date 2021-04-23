package com.imedia.designercompanion.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.imedia.designercompanion.fragments.ConnectOthersFragment;
import com.imedia.designercompanion.fragments.ContactUsFragment;
import com.imedia.designercompanion.fragments.CustomersFragment;
import com.imedia.designercompanion.fragments.HomeFragment;
import com.imedia.designercompanion.fragments.MyDesignsFragment;
import com.imedia.designercompanion.fragments.MyProfileFragment;
import com.imedia.designercompanion.fragments.WorkInProgressFragment;

public class HomeFragViewPagerAdapter extends FragmentPagerAdapter {
    public HomeFragViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MyDesignsFragment();
                break;
            case 1:
                fragment = new WorkInProgressFragment();
                break;
            default:
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "My Designs";
                break;
            case 1:
                title = "Activities";
                break;
            default:
                title = "";

        }

        return title;
    }
}
