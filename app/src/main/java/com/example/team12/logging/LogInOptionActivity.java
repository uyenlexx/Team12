package com.example.team12.logging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.team12.MainActivity;
import com.example.team12.R;

public class LogInOptionActivity extends AppCompatActivity {
    Button googleLogInButton, normalLogInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_option);

        googleLogInButton = (Button) findViewById(R.id.google_login_button);
        googleLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInOptionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        normalLogInButton = (Button) findViewById(R.id.normal_login_button);
        normalLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInOptionActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}