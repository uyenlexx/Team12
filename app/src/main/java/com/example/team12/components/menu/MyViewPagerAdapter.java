package com.example.team12.components.menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentWeeklyMenu();
            case 1:
                return new FragmentDailyMenu();
        }
        return new FragmentWeeklyMenu();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
