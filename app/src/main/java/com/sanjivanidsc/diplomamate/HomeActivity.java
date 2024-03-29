package com.sanjivanidsc.diplomamate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.sanjivanidsc.diplomamate.adapter.HomeViewPagerAdapter;
import com.sanjivanidsc.diplomamate.databinding.ActivityHomeBinding;
import com.sanjivanidsc.diplomamate.helper.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    final int MY_REQUEST_CODE = 1612;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    public static String department, year, semester;

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

        callInAppUpdate();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            Toast.makeText(this, "Downloading Start", Toast.LENGTH_SHORT).show();
            if (resultCode != RESULT_OK) {
                // If the update is cancelled or fails,
                // you can request to start the update again.
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callInAppUpdate() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }


}