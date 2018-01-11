package com.memeswow;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.memeswow.Adapters.ViewPagerAdapter;

public class LiveFeedActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);
        viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager());
        viewPager= findViewById(R.id.live_feed_pager);
        tabLayout=findViewById(R.id.live_feed_tab);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
