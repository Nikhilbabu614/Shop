package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class CustomerActivity extends AppCompatActivity {

    TabLayout tabLayout3;
    TabItem t5,t6;
    ViewPager viewPager3;
    PageAdapter3 pageAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        tabLayout3=findViewById(R.id.tablayout3);
        t5=findViewById(R.id.login3);
        t6=findViewById(R.id.cjoin);
        viewPager3=findViewById(R.id.vpager3);
        pageAdapter3=new PageAdapter3(getSupportFragmentManager(),tabLayout3.getTabCount());
        viewPager3.setAdapter(pageAdapter3);

        tabLayout3.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager3.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0||tab.getPosition()==1){
                    pageAdapter3.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager3.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout3));
    }
}