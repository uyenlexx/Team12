package com.example.team12.components.menu;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.team12.R;
import com.example.team12.components.FragmentHome;
import com.example.team12.components.FragmentSearch;

import java.util.Objects;

public class FragmentRecipeDetailed extends Fragment {
    Toolbar toolbar2;
    int backFrame;
    Fragment backFragment;
    public FragmentRecipeDetailed(int backFrame, Fragment backFragment) {
        this.backFrame = backFrame;
        this.backFragment = backFragment;
    }

//    TextView step;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View recipeDetailedView = inflater.inflate(R.layout.fragment_recipe_detailed, container, false);
//        step = recipeDetailedView.findViewById(R.id.recipe_steps);
//        step.setText(Html.fromHtml());
        String url = "https://firebasestorage.googleapis.com/v0/b/calo-a7a97.appspot.com/o/recipefriedegg.html?alt=media&token=16b42617-ff71-4af8-b115-38b083aa3ece";
        webView = recipeDetailedView.findViewById(R.id.recipe_steps);
        webView.loadUrl(url);
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
//                if (backFrame == R.id.frame_layout_search) {
//                    FragmentSearch fragment = new FragmentSearch();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(backFrame, fragment)
//                            .commit();
//                }
//                else if (backFrame == R.id.frame_layout_main) {
//                    FragmentHome fragment = new FragmentHome();
//
//                }
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(backFrame, backFragment)
                        .commit();
                // ---------------------------------------------------------
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }
}