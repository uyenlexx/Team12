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
import com.example.team12.entity.ListVariable;

public class FragmentUserProfile extends Fragment {
//    TextView tvName, tvDob, tvEmail, tvUsername, tvPassword;
//    Button btnEdit, btnSave;
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

    public void setUserInfo() {
        name.setText(ListVariable.currentUser.getName());
        dateOfBirth.setText(ListVariable.currentUser.getDateOfBirth());
        email.setText(ListVariable.currentUser.getEmail());
        username.setText(ListVariable.currentUser.getUsername());
    }

    private FragmentUserProfile getThis() {
        return this;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        tvName = view.findViewById(R.id.user_name);
//        tvDob = view.findViewById(R.id.user_dob);
//        tvEmail = view.findViewById(R.id.user_email);
//        tvUsername = view.findViewById(R.id.user_username);
//        btnEdit = view.findViewById(R.id.profile_edit_btn);
//        btnSave = view.findViewById(R.id.profile_save_btn);
//        //Set text so they can't be edited
//        tvName.setInputType(0);
//        tvDob.setInputType(0);
//        tvEmail.setInputType(0);
//        tvUsername.setInputType(0);
//        name.setText(ListVariable.currentUser.getName());
//        dateOfBirth.setText(ListVariable.currentUser.getDateOfBirth());
//        email.setText(ListVariable.currentUser.getEmail());
//        username.setText(ListVariable.currentUser.getUsername());
        name = view.findViewById(R.id.user_name);
        dateOfBirth = view.findViewById(R.id.user_dob);
        email = view.findViewById(R.id.user_email);
        username = view.findViewById(R.id.user_username);
        editButton = view.findViewById(R.id.profile_edit_btn);
        name.setInputType(0);
        dateOfBirth.setInputType(0);
        email.setInputType(0);
        username.setInputType(0);

        setUserInfo();
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
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Set text so they can be edited
//                tvName.setInputType(1);
//                tvDob.setInputType(1);
//                tvEmail.setInputType(1);
//            }
//        });
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Set text so they can't be edited
//                tvName.setInputType(0);
//                tvDob.setInputType(0);
//                tvEmail.setInputType(0);
//                ListVariable.currentUser.setName(tvName.getText().toString());
//                ListVariable.currentUser.setDateOfBirth(tvDob.getText().toString());
//                ListVariable.currentUser.setEmail(tvEmail.getText().toString());
//                ListVariable.currentUser.updateUserInfoToFirebase();
//            }
//        });
//    }

//    public TextView getName() {
//        return name;
//    }
//
//    public TextView getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public TextView getEmail() {
//        return email;
//    }
//
//    public TextView getUsername() {
//        return username;
//    }
    }
}
