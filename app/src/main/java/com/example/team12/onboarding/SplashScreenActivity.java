package com.example.team12.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.team12.MainActivity;
import com.example.team12.R;
import com.example.team12.logging.LogInActivity;
import com.example.team12.logging.SignUpActivity;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LogInActivity.class));
                finish();
            }
        }, 1000); // 3 seconds
    }
}