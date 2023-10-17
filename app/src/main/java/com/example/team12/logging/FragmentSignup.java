package com.example.team12.logging;

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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.team12.MainActivity;
import com.example.team12.R;

public class FragmentSignup extends Fragment {
    FirebaseAuth mAuth;
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
        View signUpView = inflater.inflate(R.layout.signup_fragment, container, false);
        nameEditText = (EditText) signUpView.findViewById(R.id.name_input);
        dobEditText = (EditText) signUpView.findViewById(R.id.dob_input);
        emailEditText = (EditText) signUpView.findViewById(R.id.email_input);
        usernameEditText = (EditText) signUpView.findViewById(R.id.username_input);
        passwordEditText = (EditText) signUpView.findViewById(R.id.password_input);

        signupButton = (Button) signUpView.findViewById(R.id.signup_button);
        signupGoogleButton = (Button) signUpView.findViewById(R.id.signup_button_2);

        loginTextView = (TextView) signUpView.findViewById(R.id.have_account_2);
        mAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                dob = dobEditText.getText().toString();
                email = emailEditText.getText().toString();
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                editor.putString("name", name);
                editor.putString("dob", dob);
                editor.putString("email", email);
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(getActivity(), "Account created successfully!", Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getActivity(), "Account failed to create", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

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
}
