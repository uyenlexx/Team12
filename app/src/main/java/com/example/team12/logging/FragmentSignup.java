package com.example.team12.logging;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.team12.MainActivity;
import com.example.team12.R;
import com.example.team12.components.MainScreenActivity;

public class FragmentSignup extends Fragment {
    Button signupButton, signupGoogleButton;
    EditText nameEditText, dobEditText, emailEditText, usernameEditText, passwordEditText;
    TextView loginTextView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name, dob, email, username, password;

    @Override
    public void onAttach(Context context) {
        sharedPreferences = context.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signUpView = inflater.inflate(R.layout.fragment_signup, container, false);

        nameEditText = (EditText) signUpView.findViewById(R.id.name_input);
        dobEditText = (EditText) signUpView.findViewById(R.id.dob_input);
        emailEditText = (EditText) signUpView.findViewById(R.id.email_input);
        usernameEditText = (EditText) signUpView.findViewById(R.id.username_input);
        passwordEditText = (EditText) signUpView.findViewById(R.id.password_input);

        signupButton = (Button) signUpView.findViewById(R.id.signup_button);
        signupGoogleButton = (Button) signUpView.findViewById(R.id.signup_button_2);

        loginTextView = (TextView) signUpView.findViewById(R.id.have_account_2);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                dob = dobEditText.getText().toString();
                email = emailEditText.getText().toString();
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (!isValidName(name)) {
                    nameEditText.setError("Invalid name");
                    nameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    nameEditText.requestFocus();
                    return;
                }
                nameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                if (!isValidDOB(dob)) {
                    dobEditText.setError("Invalid date of birth");
                    dobEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    dobEditText.requestFocus();
                    return;
                }
                dobEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email");
                    emailEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    emailEditText.requestFocus();
                    return;
                }
                emailEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                if (!isValidUsername(username)) {
                    usernameEditText.setError("Invalid username");
                    usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    usernameEditText.requestFocus();
                    return;
                }
                usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                if (!isValidPassword(password)) {
                    passwordEditText.setError("Password must be at least 6 characters long");
                    passwordEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    passwordEditText.requestFocus();
                    return;
                }
                passwordEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                editor.putString("name", name);
                editor.putString("dob", dob);
                editor.putString("email", email);
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Account created successfully")
                        .setTitle("Welcome to Calo4U");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                AlertDialog dialog = builder.create();
                dialog.show();

                ((LoggingActivity) getActivity()).replaceFragment(true);
            }
        });

        signupGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoggingActivity) getActivity()).replaceFragment(true);
            }
        });

        return signUpView;
    }

    private boolean isValidName(String name) {
        return name.length() > 0;
    }

    private boolean isValidDOB(String dob) {
        return dob.length() > 0;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@gmail.com");
    }

    private boolean isValidUsername(String username) {
        return username.length() > 0;
    }

    private boolean isValidPassword(String password) {
        return password.length() > 6;
    }
}
