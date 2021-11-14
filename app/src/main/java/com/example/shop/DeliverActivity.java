package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class DeliverActivity extends AppCompatActivity {
    TabLayout tabLayout2;
    TabItem t3,t4;
    ViewPager viewPager2;
    PageAdapter2 pageAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver);

        tabLayout2=findViewById(R.id.tablayout2);
        t3=findViewById(R.id.login2);
        t4=findViewById(R.id.join);
        viewPager2=findViewById(R.id.vpager2);
        pageAdapter2=new PageAdapter2(getSupportFragmentManager(),tabLayout2.getTabCount());
        viewPager2.setAdapter(pageAdapter2);

        tabLayout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0||tab.getPosition()==1){
                    pageAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout2));
    }
}