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
import com.example.team12.components.listener.IngredientDetailCallback;
import com.example.team12.components.listener.RecipeDetailCallback;
import com.example.team12.components.listener.UserFavoriteIngredientCallback;
import com.example.team12.components.listener.UserFavoriteRecipeCallback;
import com.example.team12.entity.Ingredient;
import com.example.team12.entity.IngredientDetail;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.RecipeDetail;
import com.example.team12.entity.User;
import com.google.firebase.storage.StorageReference;

public class FragmentIngredientDetailed extends Fragment {
    TextView tvCaloriesValue, tvProteinValue, tvFatValue, tvCarbsValue, tvIngredientName, tvDetail, tvIngredients;
    ImageView imgIngredient;
    Button btnAddToFavorite;
    Toolbar toolbar2;
    int backFrame;
    boolean isFavorite = false;
    Fragment backFragment;
    public FragmentIngredientDetailed(int backFrame, Fragment backFragment) {
        this.backFrame = backFrame;
        this.backFragment = backFragment;
    }
    StorageReference storageReference;

    //    TextView step;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ingredientDetailedView = inflater.inflate(R.layout.fragment_ingredient_detailed, container, false);
//        step = ingredientDetailedView.findViewById(R.id.recipe_steps);
//        step.setText(Html.fromHtml());
//        String url = "https://firebasestorage.googleapis.com/v0/b/calo-a7a97.appspot.com/o/recipefriedegg.html?alt=media&token=16b42617-ff71-4af8-b115-38b083aa3ece";
//        webView = ingredientDetailedView.findViewById(R.id.ingredient_steps);
//        webView.loadUrl(url);
        return ingredientDetailedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Check Ingredient", "Current status: " + (ListVariable.currentIngredient == null));
        btnAddToFavorite = view.findViewById(R.id.add_to_favorite_btn);
        tvIngredientName = view.findViewById(R.id.ingredient_title);
        tvCaloriesValue = view.findViewById(R.id.ingredient_calories_number);
        tvCarbsValue = view.findViewById(R.id.ingredient_carbs_number);
        tvFatValue = view.findViewById(R.id.ingredient_fat_number);
        tvProteinValue = view.findViewById(R.id.ingredient_protein_number);
        tvDetail = view.findViewById(R.id.ingredient_details);
        imgIngredient = view.findViewById(R.id.ingredient_image);


        if (ListVariable.currentIngredient != null) {
            //Get Ingredient detail
            IngredientDetail.getIngredientDetailById(ListVariable.currentIngredient.getIngredientId(), new IngredientDetailCallback() {
                @Override
                public void onCallback(IngredientDetail value) {
                    tvIngredientName.setText(ListVariable.currentIngredient.getIngredientName());
                    tvCaloriesValue.setText(String.valueOf(value.getCalories()));
                    tvCarbsValue.setText(String.valueOf(value.getCarbs()));
                    tvFatValue.setText(String.valueOf(value.getFat()));
                    tvProteinValue.setText(String.valueOf(value.getProtein()));
                    tvDetail.setText(value.getDescription());
//                    tvIngredients.setText(value.getIngredientName());
                    String url = ListVariable.currentIngredient.getImageURL();

                    Glide.with(requireContext())
                            .load(url)
                            .error(R.drawable.img_trending_1)
                            .into(imgIngredient);
                }
            });
            User.checkFavoriteIngredient(ListVariable.currentUser.getUserId(), ListVariable.currentIngredient.getIngredientId(), new UserFavoriteIngredientCallback() {
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
                    ListVariable.currentUser.removeFavoriteIngredientFromFirebase(ListVariable.currentIngredient.getIngredientId());
                    btnAddToFavorite.setText("Add to favorite");
                    isFavorite = false;
                }
                else {
                    ListVariable.currentUser.addFavoriteIngredientToFirebase(ListVariable.currentIngredient.getIngredientId());
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
