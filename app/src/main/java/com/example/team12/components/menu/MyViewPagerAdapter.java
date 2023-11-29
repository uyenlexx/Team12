package com.example.team12.components.menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    private Fragment menuFragment;
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Fragment fragment) {
        super(fragmentActivity);
        this.menuFragment = fragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentDailyMenu(menuFragment);
            case 1:
                return new FragmentWeeklyMenu();
        }
        return new FragmentDailyMenu(menuFragment);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
