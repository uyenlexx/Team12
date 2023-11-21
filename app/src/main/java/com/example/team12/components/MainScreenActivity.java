package com.example.team12.components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.team12.R;
import com.example.team12.databinding.ActivityMainScreenBinding;

public class MainScreenActivity extends AppCompatActivity {
    ActivityMainScreenBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addFragment();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home_tab)
                replaceFragment(new FragmentHome());
            else if (id == R.id.search_tab)
                replaceFragment(new FragmentSearch());
            else if (id == R.id.calculate_tab)
                replaceFragment(new FragmentCalculate());
            else if (id == R.id.menu_tab)
                replaceFragment(new FragmentMenu());
            else if (id == R.id.profile_tab)
                replaceFragment(new FragmentProfile());

            return true;
        });
    }

    public void addFragment() {
        FragmentHome fragment = new FragmentHome();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_main, fragment);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, fragment);
        fragmentTransaction.commit();
    }
}