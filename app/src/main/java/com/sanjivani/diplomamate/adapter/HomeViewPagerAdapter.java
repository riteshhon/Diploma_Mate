package com.sanjivani.diplomamate.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.sanjivani.diplomamate.home.HomeFragment;
import com.sanjivani.diplomamate.home.MeFragment;

public class HomeViewPagerAdapter extends FragmentStateAdapter {


    public HomeViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new MeFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
