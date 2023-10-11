package com.example.team12.logging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.team12.MainActivity;
import com.example.team12.R;

public class SignUpOptionActivity extends AppCompatActivity {
    Button googleSignUpButton, normalSignUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_option);

        googleSignUpButton = (Button) findViewById(R.id.google_signup_button);
        googleSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpOptionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        normalSignUpButton = (Button) findViewById(R.id.normal_signup_button);
normalSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpOptionActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}