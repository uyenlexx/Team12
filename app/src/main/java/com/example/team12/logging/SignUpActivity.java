package com.example.team12.logging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.team12.MainActivity;
import com.example.team12.R;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton;
    TextView logInTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = (Button) findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logInTextView = (TextView) findViewById(R.id.have_account_2);
        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInOptionActivity.class);
                startActivity(intent);
            }
        });
    }
}