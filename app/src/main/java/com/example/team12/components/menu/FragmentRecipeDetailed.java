package com.example.team12.components.menu;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.team12.R;
import com.example.team12.components.FragmentHome;
import com.example.team12.components.FragmentSearch;
import com.example.team12.components.listener.RecipeDetailCallback;
import com.example.team12.components.listener.UserFavoriteRecipeCallback;
import com.example.team12.entity.IngredientDetail;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.RecipeDetail;
import com.example.team12.entity.User;

import java.util.List;
import java.util.Objects;

public class FragmentRecipeDetailed extends Fragment {
    TextView tvCaloriesValue, tvProteinValue, tvFatValue, tvCarbsValue, tvRecipeName;
    Button btnAddToFavorite;
    Toolbar toolbar2;
    int backFrame;
    boolean isFavorite = false;
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
        Log.i("Check Recipe", "Current status: " + (ListVariable.currentRecipe == null));
        btnAddToFavorite = view.findViewById(R.id.add_to_favorite_btn);
        tvRecipeName = view.findViewById(R.id.recipe_title);
        tvCaloriesValue = view.findViewById(R.id.recipe_calories_number);
        tvCarbsValue = view.findViewById(R.id.recipe_carbs_number);
        tvFatValue = view.findViewById(R.id.recipe_fat_number);
        tvProteinValue = view.findViewById(R.id.recipe_protein_number);
        if (ListVariable.currentRecipe != null) {
            //Get recipe detail
            RecipeDetail.getRecipeDetailById(ListVariable.currentRecipe.getRecipeId(), new RecipeDetailCallback() {
                @Override
                public void onCallback(RecipeDetail value) {
                    tvRecipeName.setText(ListVariable.currentRecipe.getRecipeName());
                    tvCaloriesValue.setText(String.valueOf(value.getCalories()));
                    tvCarbsValue.setText(String.valueOf(value.getCarbs()));
                    tvFatValue.setText(String.valueOf(value.getFat()));
                    tvProteinValue.setText(String.valueOf(value.getProtein()));
                }
            });
            User.checkFavoriteRecipe(ListVariable.currentUser.getUserId(), ListVariable.currentRecipe.getRecipeId(), new UserFavoriteRecipeCallback() {
                @Override
                public void onCallback(boolean value) {
                    if (value) {
                        btnAddToFavorite.setText("Remove from favorite");
                        isFavorite = true;
                    }
                }
            });
        }

        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    ListVariable.currentUser.removeFavoriteRecipeFromFirebase(ListVariable.currentRecipe.getRecipeId());
                    btnAddToFavorite.setText("Add to favorite");
                    isFavorite = false;
                }
                else {
                    ListVariable.currentUser.addFavoriteRecipeToFirebase(ListVariable.currentRecipe.getRecipeId());
                    btnAddToFavorite.setText("Remove from favorite");
                    isFavorite = true;
                }
            }
        });



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
