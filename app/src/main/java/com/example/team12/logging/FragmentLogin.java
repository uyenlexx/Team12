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

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.team12.MainActivity;
import com.example.team12.R;
import com.example.team12.components.MainScreenActivity;

public class FragmentLogin extends Fragment {
    Button loginButton, loginGoogleButton;
    EditText usernameEditText, passwordEditText;
    TextView signupTextView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String username, password;

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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

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

                Intent intent = new Intent(getActivity(), MainScreenActivity.class);
                startActivity(intent);
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
        return username.equals("admin");
    }

    private boolean isValidPassword(String password) {
        return password.equals("admin");
    }
}
