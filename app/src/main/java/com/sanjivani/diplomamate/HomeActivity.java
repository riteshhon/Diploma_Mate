package com.sanjivani.diplomamate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sanjivani.diplomamate.adapter.HomeViewPagerAdapter;
import com.sanjivani.diplomamate.databinding.ActivityHomeBinding;
import com.sanjivani.diplomamate.helper.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    public static String log, department, year, semester;

    ActivityHomeBinding binding;
    HomeViewPagerAdapter homeViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sh = getSharedPreferences("BasicDetails", MODE_PRIVATE);
        department = sh.getString("department", "");
        year = sh.getString("year", "");
        semester = sh.getString("semester", "");


        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        binding.mainViewPager.setUserInputEnabled(false);
        binding.mainViewPager.setAdapter(homeViewPagerAdapter);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            binding.mainTabLayout.addTab(binding.mainTabLayout.newTab().setIcon(R.drawable.ic_home));
            binding.mainTabLayout.addTab(binding.mainTabLayout.newTab().setIcon(R.drawable.ic_me));
//            binding.mainTabLayout.addTab(binding.mainTabLayout.newTab().setIcon(R.drawable.logo));

            binding.mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    binding.mainViewPager.setCurrentItem(tab.getPosition());
                    if (tab.getPosition() == 0) {
                        Utils.whiteIconStatusBar(HomeActivity.this, R.color.primary_color);
                    } else if (tab.getPosition() == 1) {
                        Utils.whiteIconStatusBar(HomeActivity.this, R.color.primary_color);
                    }
//                    else if (tab.getPosition() == 2) {
//                        Utils.whiteIconStatusBar(HomeActivity.this, R.color.primary_color);
//                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            binding.mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    binding.mainTabLayout.selectTab(binding.mainTabLayout.getTabAt(position));
                    if (position == 0) {
                        Utils.whiteIconStatusBar(HomeActivity.this, R.color.primary_color);
                    } else if (position == 1) {
                        Utils.whiteIconStatusBar(HomeActivity.this, R.color.primary_color);
                    }
//                    else if (position == 2) {
//                        Utils.whiteIconStatusBar(HomeActivity.this, R.color.primary_color);
//                    }
                }
            });
        });


    }


    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

}