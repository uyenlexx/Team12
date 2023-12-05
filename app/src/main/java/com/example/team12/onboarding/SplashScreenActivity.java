package com.example.team12.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.team12.MainActivity;
import com.example.team12.R;
import com.example.team12.components.MainScreenActivity;
import com.example.team12.components.listener.UserLogInCallback;
import com.example.team12.components.preference.DataLocalManager;
import com.example.team12.entity.Ingredient;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.Recipe;
import com.example.team12.entity.User;
import com.example.team12.logging.LoggingActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.CompletableFuture;


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
                if (DataLocalManager.getUserLoggedIn() == -1) {
                    startActivity(new Intent(SplashScreenActivity.this, LoggingActivity.class));
                } else {
                    User.getUserByUserId(DataLocalManager.getUserLoggedIn(), new UserLogInCallback() {
                        @Override
                        public void onCallback(User user) {
                            if (user.getUserId() == -1) {
                                DataLocalManager.setUserLoggedIn(-1);
                                startActivity(new Intent(SplashScreenActivity.this, LoggingActivity.class));
                            }
                            else {
                                ListVariable.currentUser = user;
                                DataLocalManager.setUserLoggedIn(user.getUserId());
                                CompletableFuture<Void> future = setUpFirebase();
                                future.thenRunAsync(() -> {
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    startActivity(new Intent(SplashScreenActivity.this, MainScreenActivity.class));
                                });
                            }
                        }
                    });
                }
                finish();
            }
        }, 1000); // 3 seconds
    }

    protected CompletableFuture<Void> setUpFirebase() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Recipe.setUpFirebase();
        Ingredient.setUpFirebase();
        future.complete(null);
        return future;
    }
}