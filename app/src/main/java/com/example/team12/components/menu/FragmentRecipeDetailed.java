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
    TextView title, calories, protein, carbs, fat, details;

    public void showData(String title, Double calories,
                         Double protein, Double carbs,
                         Double fat, String details, String url
                        ) {
        this.title.setText(title);
        this.calories.setText(calories.toString());
        this.protein.setText(protein.toString());
        this.carbs.setText(carbs.toString());
        this.fat.setText(fat.toString());
        this.details.setText(details);
        webView.loadUrl(url);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View recipeDetailedView = inflater.inflate(R.layout.fragment_recipe_detailed, container, false);
//        step = recipeDetailedView.findViewById(R.id.recipe_steps);
//        step.setText(Html.fromHtml());
        String url = "https://firebasestorage.googleapis.com/v0/b/calo-a7a97.appspot.com/o/recipefriedegg.html?alt=media&token=16b42617-ff71-4af8-b115-38b083aa3ece";
        webView = recipeDetailedView.findViewById(R.id.recipe_steps);
        title = recipeDetailedView.findViewById(R.id.recipe_title);
        calories = recipeDetailedView.findViewById(R.id.recipe_calories_number);
        protein = recipeDetailedView.findViewById(R.id.recipe_protein_number);
        carbs = recipeDetailedView.findViewById(R.id.recipe_carbs_number);
        fat = recipeDetailedView.findViewById(R.id.recipe_fat_number);
        details = recipeDetailedView.findViewById(R.id.recipe_details);


        showData("Fried Egg", 100.0, 10.0, 10.0, 10.0, "Fried egg is a dish made of egg, " +
                "fried in oil or butter. Fried eggs are traditionally eaten for breakfast in " +
                "many countries but may also be served at other times of the day. There is a " +
                "wide variety of ways to cook fried eggs. Many cuisines have their own " +
                "versions of fried eggs, such as the sunny side up, the Denver omelette, " +
                "the Scotch egg, the Thai fried egg, and others.", url);
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
