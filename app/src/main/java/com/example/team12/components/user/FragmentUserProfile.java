package com.example.team12.components.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.example.team12.R;
import com.example.team12.components.FragmentUser;
import com.example.team12.entity.ListVariable;

public class FragmentUserProfile extends Fragment {
    TextView tvName, tvDob, tvEmail, tvUsername, tvPassword;
    Button btnEdit, btnSave;
    Toolbar toolbar4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userProfileView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return userProfileView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.user_name);
        tvDob = view.findViewById(R.id.user_dob);
        tvEmail = view.findViewById(R.id.user_email);
        tvUsername = view.findViewById(R.id.user_username);
        tvPassword = view.findViewById(R.id.user_password);
        btnEdit = view.findViewById(R.id.profile_edit_btn);
        btnSave = view.findViewById(R.id.profile_save_btn);
        //Set text so they can't be edited
        tvName.setInputType(0);
        tvDob.setInputType(0);
        tvEmail.setInputType(0);
        tvUsername.setInputType(0);
        tvPassword.setInputType(0);
        tvName.setText(ListVariable.currentUser.getName());
        tvDob.setText(ListVariable.currentUser.getDateOfBirth());
        tvEmail.setText(ListVariable.currentUser.getEmail());
        tvUsername.setText(ListVariable.currentUser.getUsername());

        toolbar4 = view.findViewById(R.id.profile_toolbar);

        toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to fragment user
                FragmentUser fragment = new FragmentUser();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, fragment)
                        .commit();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set text so they can be edited
                tvName.setInputType(1);
                tvDob.setInputType(1);
                tvEmail.setInputType(1);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set text so they can't be edited
                tvName.setInputType(0);
                tvDob.setInputType(0);
                tvEmail.setInputType(0);
                ListVariable.currentUser.setName(tvName.getText().toString());
                ListVariable.currentUser.setDateOfBirth(tvDob.getText().toString());
                ListVariable.currentUser.setEmail(tvEmail.getText().toString());
                ListVariable.currentUser.updateUserInfoToFirebase();
            }
        });
    }
}
