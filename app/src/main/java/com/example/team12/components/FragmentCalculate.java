package com.example.team12.components;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team12.R;
import com.example.team12.components.calculator.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragmentCalculate extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View calculateView = inflater.inflate(R.layout.fragment_calculate, container, false);

        tabLayout = calculateView.findViewById(R.id.tab_layout);
        viewPager2 = calculateView.findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(getActivity());
        viewPager2.setAdapter(myViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // When a tab is selected, switch to the corresponding page in the ViewPager.
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            // When a tab is unselected, switch to the corresponding page in the ViewPager.
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            // When a tab is reselected, switch to the corresponding page in the ViewPager.
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // When a page is selected, switch to the corresponding tab in the TabLayout.
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return calculateView;
    }
}