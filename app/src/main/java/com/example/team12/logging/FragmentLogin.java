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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private static final int RC_SIGN_IN = 1000;
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
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

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
                Intent signInIntent = gsc.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Sign in failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Date of birth", "1231");
                    hashMap.put("email", user.getEmail());
                    hashMap.put("name", user.getDisplayName());
                    hashMap.put("uid", user.getUid());
                    database.getReference().child("Users").child(user.getUid()).setValue(hashMap);

                    Toast.makeText(getActivity(), "Authentication success.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
    });
    }

    private boolean isValidUsername(String username) {
        return username.length() > 0;
    }

    private boolean isValidPassword(String password) {
        return password.length() > 0;
    }
}
