package com.example.team12.logging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.team12.R;
import com.example.team12.entity.Recipe;

public class LoggingActivity extends AppCompatActivity {
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
        addFragment();
        addSetUp();
    }

    public void addFragment() {
        FragmentLogin fragment = new FragmentLogin();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.logging_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Boolean isLogin) {
        if (isLogin) {
            fragment = new FragmentLogin();
        } else {
            fragment = new FragmentSignup();
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.logging_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void addSetUp() {
        Recipe.setUpFirebase();
    }
}