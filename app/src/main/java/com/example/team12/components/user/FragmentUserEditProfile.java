package com.example.team12.components.user;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.team12.R;
import com.example.team12.components.FragmentUser;
import com.example.team12.entity.ListVariable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentUserEditProfile extends Fragment {
    Toolbar toolbar;
    EditText name;
    EditText dob;
    EditText email;
    TextView username;
    Button saveButton;
    Button cancelButton;
    FragmentUserProfile userProfile;
    Calendar calendar = Calendar.getInstance();

    public FragmentUserEditProfile(FragmentUserProfile userProfile) {
        this.userProfile = userProfile;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_profile_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.user_name);
        dob = view.findViewById(R.id.user_dob);
        email = view.findViewById(R.id.user_email);
        username = view.findViewById(R.id.user_username);

        name.setText(ListVariable.currentUser.getName());
        dob.setText(ListVariable.currentUser.getDateOfBirth());
        email.setText(ListVariable.currentUser.getEmail());
        username.setText(ListVariable.currentUser.getUsername());
//        password.setText("password");

        saveButton = view.findViewById(R.id.profile_save_btn);
        cancelButton = view.findViewById(R.id.profile_cancel_btn);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                updateLabel();
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListVariable.currentUser.setName(name.getText().toString());
                ListVariable.currentUser.setDateOfBirth(dob.getText().toString());
                ListVariable.currentUser.setEmail(email.getText().toString());
                ListVariable.currentUser.updateUserInfoToFirebase();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, userProfile)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, userProfile)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        toolbar = view.findViewById(R.id.profile_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to fragment user
                FragmentUser fragment = new FragmentUser();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, fragment)
                        .commit();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        dob.setText(sdf.format(calendar.getTime()));
    }
}
