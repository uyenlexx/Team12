package com.example.team12.components;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team12.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreenActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentHome fragmentHome;
    FragmentSearch fragmentSearch;
    FragmentCalculate fragmentCalculate;
    FragmentMenu fragmentMenu;
    FragmentUser fragmentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        fragmentHome = new FragmentHome();
        fragmentHome.setBottomNavigationView(bottomNavigationView);
        fragmentSearch = new FragmentSearch();
        fragmentCalculate = new FragmentCalculate();
        fragmentMenu = new FragmentMenu();
        fragmentUser = new FragmentUser();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragmentHome).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home_tab) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragmentHome).commit();
                    return true;
                } else if (itemId == R.id.search_tab) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragmentSearch).commit();
                    return true;
                } else if (itemId == R.id.calculate_tab) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragmentCalculate).commit();
                    return true;
                } else if (itemId == R.id.menu_tab) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragmentMenu).commit();
                    return true;
                } else if (itemId == R.id.user_tab) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragmentUser).commit();
                    return true;
                }
                return false;
            }
        });
    }

    public void screenTransaction() {

    }
    public void navigateToCalculateFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, fragmentCalculate).commit();
    }

}