package com.example.shop;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter2 extends FragmentPagerAdapter {
    int tabcount;
    public PageAdapter2(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Deliverlogin();
            case 1 : return new DeliverJoin();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
