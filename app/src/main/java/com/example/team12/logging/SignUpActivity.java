package com.example.team12.logging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team12.MainActivity;
import com.example.team12.R;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton;
    TextView logInTextView;

    EditText nameEditText;
    EditText dobEditText;
    EditText emailEditText;
    EditText usernameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = (Button) findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidPassword(passwordEditText.getText().toString())
                      && isValidEmail(emailEditText.getText().toString())) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
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

        nameEditText = (EditText) findViewById(R.id.name_input);
        dobEditText = (EditText) findViewById(R.id.dob_input);
        emailEditText = (EditText) findViewById(R.id.email_input);
        usernameEditText = (EditText) findViewById(R.id.username_input);
        passwordEditText = (EditText) findViewById(R.id.password_input);
    }

    public boolean isValidPassword(String password) {
        boolean state = false;
        if (password.length() >= 6) {
            state = true;
        } else {
            Toast.makeText(SignUpActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            passwordEditText.setText("");
            passwordEditText.requestFocus();
            state = false;
        }
        return state;
    }

    public boolean isValidEmail(String email) {
        boolean state = false;
        if (email.contains("@") && email.contains(".")) {
            state = true;
        } else {
            Toast.makeText(SignUpActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            emailEditText.setText("");
            emailEditText.requestFocus();
            state = false;
        }
        return state;
    }
}