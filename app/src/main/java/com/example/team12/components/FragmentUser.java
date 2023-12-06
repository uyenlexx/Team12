package com.example.team12.components;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.team12.R;
import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.preference.DataLocalManager;
import com.example.team12.components.user.FragmentUserFavorites;
import com.example.team12.components.user.FragmentUserProfile;
import com.example.team12.components.user.FragmentUserSettings;
import com.example.team12.entity.ListVariable;
import com.example.team12.logging.LoggingActivity;

public class FragmentUser extends Fragment {
    FrameLayout frameLayout;
    TextView username;
    CardView profileCardView;
    CardView favoritesCardView;
    CardView settingsCardView;
    CardView logoutCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_user, container, false);
        return profileView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.user_name);
        username.setText(ListVariable.currentUser.getUsername());

        frameLayout = view.findViewById(R.id.frame_layout_user);
        profileCardView = view.findViewById(R.id.profile_card);
        favoritesCardView = view.findViewById(R.id.favorite_card);
        settingsCardView = view.findViewById(R.id.settings_card);
        logoutCardView = view.findViewById(R.id.logout_card);

        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUserProfile fragment = new FragmentUserProfile();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        favoritesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUserFavorites fragment = new FragmentUserFavorites();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, fragment)
//                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        settingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUserSettings fragment = new FragmentUserSettings();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListVariable.currentUser = null;
                ListVariable.currentRecipe = null;
                ListVariable.currentUser = null;

                DataLocalManager.setUserLoggedIn(-1);
                Intent intent = new Intent(getActivity(), LoggingActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}