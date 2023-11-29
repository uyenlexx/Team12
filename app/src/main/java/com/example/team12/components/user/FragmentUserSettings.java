package com.example.team12.components.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.example.team12.R;
import com.example.team12.components.FragmentSearch;
import com.example.team12.components.FragmentUser;

public class FragmentUserSettings extends Fragment {
    Toolbar toolbar3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userSettingsView = inflater.inflate(R.layout.fragment_user_settings, container, false);
        return userSettingsView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar3 = view.findViewById(R.id.settings_toolbar);

        toolbar3.setNavigationOnClickListener(new View.OnClickListener() {
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
}