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
import androidx.fragment.app.Fragment;

import com.example.team12.MainActivity;
import com.example.team12.R;

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
        View loginView = inflater.inflate(R.layout.login_fragment, container, false);

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
                Intent intent = new Intent(getActivity(), MainActivity.class);
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
}
