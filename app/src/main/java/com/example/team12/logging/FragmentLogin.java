package com.example.team12.logging;

import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.team12.MainActivity;
import com.example.team12.R;
import com.example.team12.components.MainScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentLogin extends Fragment {
    Button loginButton, loginGoogleButton;
    EditText usernameEditText, passwordEditText;
    TextView signupTextView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String username, password;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference, NonEmailUsername;
    @Override
    public void onAttach(Context context) {
        sharedPreferences = context.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);

        usernameEditText = (EditText) loginView.findViewById(R.id.username_input);
        passwordEditText = (EditText) loginView.findViewById(R.id.password_input);
        loginButton = (Button) loginView.findViewById(R.id.login_button);
        loginGoogleButton = (Button) loginView.findViewById(R.id.login_button_2);
        signupTextView = (TextView) loginView.findViewById(R.id.dont_have_account_2);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://calo-a7a97-default-rtdb.firebaseio.com/");
        reference = database.getReference();
//        NonEmailUsername = database.getReference().child("Users");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                DatabaseReference emailRef = NonEmailUsername.child(username).child("email");
//                if (!username.contains("@")) {
//                    username = emailRef.getKey();
//                    Log.d("FRAGMENTLOGIN", username);
//                }
                if (!isValidUsername(username)) {
                    usernameEditText.setError("Invalid username");
                    usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    usernameEditText.requestFocus();
                    return;
                }
                usernameEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                if (!isValidPassword(password)) {
                    passwordEditText.setError("Invalid password");
                    passwordEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border_red, null));
                    return;
                }
                passwordEditText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_border, null));

                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getActivity(), "Authentication success.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MainScreenActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoggingActivity) getActivity()).replaceFragment(false);
            }
        });

        return loginView;
    }

    private boolean isValidUsername(String username) {
        return username.length() > 0;
    }

    private boolean isValidPassword(String password) {
        return password.length() > 0;
    }
}
