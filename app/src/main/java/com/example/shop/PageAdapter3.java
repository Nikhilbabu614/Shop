package com.example.shop;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter3 extends FragmentPagerAdapter {
    int tabcount;
    public PageAdapter3(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new customer_login();
            case 1 : return new customer_join();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
