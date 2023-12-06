package com.example.team12.components.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.team12.R;
import com.example.team12.components.listener.RecipeDetailCallback;
import com.example.team12.components.listener.UserFavoriteRecipeCallback;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.RecipeDetail;
import com.example.team12.entity.User;
import com.google.firebase.storage.StorageReference;

public class FragmentRecipeDetailed extends Fragment {
    TextView tvCaloriesValue, tvProteinValue, tvFatValue, tvCarbsValue, tvRecipeName, tvDetail, tvIngredients;
    ImageView imgRecipe;
    Button btnAddToFavorite;
    Toolbar toolbar2;
    int backFrame;
    boolean isFavorite = false;
    Fragment backFragment;
    public FragmentRecipeDetailed(int backFrame, Fragment backFragment) {
        this.backFrame = backFrame;
        this.backFragment = backFragment;
    }
    StorageReference storageReference;



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
        webView = recipeDetailedView.findViewById(R.id.ingredient_steps);
        webView.loadUrl(url);
//        webView = recipeDetailedView.findViewById(R.id.ingredient_steps);
//        title = recipeDetailedView.findViewById(R.id.recipe_title);
//        calories = recipeDetailedView.findViewById(R.id.recipe_calories_number);
//        protein = recipeDetailedView.findViewById(R.id.recipe_protein_number);
//        carbs = recipeDetailedView.findViewById(R.id.recipe_carbs_number);
//        fat = recipeDetailedView.findViewById(R.id.recipe_fat_number);
//        details = recipeDetailedView.findViewById(R.id.recipe_details);
//
//
//        showData("Fried Egg", 100.0, 10.0, 10.0, 10.0, "Fried egg is a dish made of egg, " +
//                "fried in oil or butter. Fried eggs are traditionally eaten for breakfast in " +
//                "many countries but may also be served at other times of the day. There is a " +
//                "wide variety of ways to cook fried eggs. Many cuisines have their own " +
//                "versions of fried eggs, such as the sunny side up, the Denver omelette, " +
//                "the Scotch egg, the Thai fried egg, and others.", url);
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
        tvDetail = view.findViewById(R.id.recipe_details);
        tvIngredients = view.findViewById(R.id.recipe_ingredients);
        imgRecipe = view.findViewById(R.id.recipe_image);


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
                    tvDetail.setText(ListVariable.currentRecipe.getDescription());
                    webView.loadUrl(value.getStep());
                    String ingredients = "";
                    for (String ingredientName: value.getIngredient().keySet()) {
                        ingredients += value.getIngredient().get(ingredientName).second.first + " " + value.getIngredient().get(ingredientName).second.second + " " + ingredientName + "\n";
                    }
                    tvIngredients.setText(ingredients);
//                    storageReference = ListVariable .storage.getReferenceFromUrl(ListVariable.currentRecipe.getImageURL());
                    String url = ListVariable.currentRecipe.getImageURL();
//                    Glide.with(Objects.requireNonNull(getContext())).load(storageReference).into(imgRecipe);

                    Glide.with(requireContext())
                            .load(url)
                            .error(R.drawable.img_trending_1)
                            .into(imgRecipe);
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
