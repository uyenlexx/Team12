package com.example.team12.logging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.team12.MainActivity;
import com.example.team12.R;
import com.example.team12.components.MainScreenActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class FragmentSignup extends Fragment {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference, existedUsername;
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
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://calo-a7a97-default-rtdb.firebaseio.com/");
        reference = database.getReference();
        existedUsername = database.getReference().child("Users");


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

                if (!isValidUsername(username)) {
                    usernameEditText.setError("Invalid username");
                    usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    usernameEditText.requestFocus();
                    return;
                }
                usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                editor.putString("name", name);
                editor.putString("dob", dob);
                editor.putString("email", email);
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();

                DatabaseReference userNameRef = existedUsername.child(username);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                userNameRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task1) {
                        if (task1.isSuccessful()) {
                            DataSnapshot snapshot = task1.getResult();
                            if (snapshot.exists()) {
                                usernameEditText.setError("Username already existed");
                                usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                                usernameEditText.requestFocus();
                                usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));
                                Log.d("EXISTEDNAME", username);
                            } else {
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (task.isSuccessful()) {

                                                    // Sign in success, update UI with the signed-in user's information
                                                    HashMap<String, String> user = new HashMap<>();
                                                    user.put("uid", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                                    user.put("name", name);
                                                    user.put("email", email);
                                                    user.put("Date of birth", dob);
                                                    reference.child("Users")
                                                            .child(username)
                                                            .setValue(user);
                                                    builder.setMessage("Account created successfully")
                                                            .setTitle("Welcome to Calo4U");

                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();

                                                    ((LoggingActivity) getActivity()).replaceFragment(true);
                                                    Log.d("NAMEOFTHEUSER", String.valueOf(existedUsername));

                                                } else {
                                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                                    switch (errorCode) {

                                                        case "ERROR_INVALID_EMAIL":
                                                            emailEditText.setError("Invalid Email");
                                                            emailEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                                                            emailEditText.requestFocus();
                                                            emailEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));
                                                            break;

                                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                                            emailEditText.setError("Email already existed!");
                                                            emailEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                                                            emailEditText.requestFocus();
                                                            emailEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));
                                                            break;

                                                        case "ERROR_WEAK_PASSWORD":
                                                            passwordEditText.setError("Password must be at least 6 characters long");
                                                            passwordEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                                                            passwordEditText.requestFocus();
                                                            passwordEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));
                                                            break;
                                                    }
                                                    // If sign in fails, display a message to the user.
                                                    Toast.makeText(getActivity(), "Account failed to create", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        });
                                Log.d("FragmentSignup Tag", "Usable");
                            }
                        } else {
                            Log.d("SIGNUPERROR", task1.getException().getMessage());
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

    private boolean isValidName(String name) {
        return name.length() > 0;
    }

    private boolean isValidDOB(String dob) {
        return dob.length() > 0;
    }

    private boolean isValidUsername(String username) {
        return username.length() > 0;
    }

}
