package com.example.team12.components.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.team12.R;
import com.example.team12.components.FragmentUser;

public class FragmentUserProfile extends Fragment {
    Toolbar toolbar4;
    TextView name;
    TextView dateOfBirth;
    TextView email;
    TextView username;
    Button editButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userProfileView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return userProfileView;
    }

    public void setUserInfo(String textName, String textdob, String textEmail, String textUsername) {
        name.setText(textName);
        dateOfBirth.setText(textdob);
        email.setText(textEmail);
        username.setText(textUsername);
        System.out.println("set text");
    }

    private FragmentUserProfile getThis() {
        return this;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.user_name);
        dateOfBirth = view.findViewById(R.id.user_dob);
        email = view.findViewById(R.id.user_email);
        username = view.findViewById(R.id.user_username);
        editButton = view.findViewById(R.id.profile_edit_btn);

        setUserInfo("Anemo Kazuha", "29/10/2021", "ilovekazuha123@hotmail.com", "Kazuma");
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUserEditProfile editProfile = new FragmentUserEditProfile(getThis());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, editProfile)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        toolbar4 = view.findViewById(R.id.profile_toolbar);

        toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to fragment user
                FragmentUser fragment = new FragmentUser();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
    }

    public TextView getName() {
        return name;
    }

    public TextView getDateOfBirth() {
        return dateOfBirth;
    }

    public TextView getEmail() {
        return email;
    }

    public TextView getUsername() {
        return username;
    }
}
