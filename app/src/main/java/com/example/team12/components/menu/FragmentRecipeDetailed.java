package com.example.team12.components.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.example.team12.R;
import com.example.team12.components.FragmentHome;
import com.example.team12.components.FragmentSearch;

public class FragmentRecipeDetailed extends Fragment {
    Toolbar toolbar2;
    int backFrame;

    public FragmentRecipeDetailed(int backFrame) {
        this.backFrame = backFrame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View recipeDetailedView = inflater.inflate(R.layout.fragment_recipe_detailed, container, false);
        return recipeDetailedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar2 = view.findViewById(R.id.toolbar_2);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to fragment search
                if (backFrame == R.id.frame_layout_search) {
                    FragmentSearch fragment = new FragmentSearch();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(backFrame, fragment)
                            .commit();
                }
                else if (backFrame == R.id.frame_layout_main) {
                    FragmentHome fragment = new FragmentHome();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(backFrame, fragment)
                            .commit();
                }
            }
        });
    }
}
