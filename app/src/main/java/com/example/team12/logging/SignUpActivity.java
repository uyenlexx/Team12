package com.example.team12.logging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team12.MainActivity;
import com.example.team12.MainActivity;
import com.example.team12.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    TextView logInTextView;

    EditText nameEditText;
    EditText dobEditText;
    EditText emailEditText;
    EditText usernameEditText;
    EditText passwordEditText;

    AppCompatButton signUpButton;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = (AppCompatButton) findViewById(R.id.signup_button);
        usernameEditText = (EditText) findViewById(R.id.username_input);
        passwordEditText = (EditText) findViewById(R.id.password_input);
        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidEmail(username) && isValidPassword(password)) {

                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(SignUpActivity.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    } else {

                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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