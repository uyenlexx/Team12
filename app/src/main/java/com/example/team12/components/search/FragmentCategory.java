package com.example.team12.components.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.components.FragmentSearch;
import com.example.team12.components.FragmentUser;
import com.example.team12.components.home.ItemClass;
import com.example.team12.components.home.ItemInterface;
import com.example.team12.components.listener.IngredientCategoryCallback;
import com.example.team12.components.listener.IngredientDetailCallback;
import com.example.team12.components.listener.RecipeCategoryCallback;
import com.example.team12.components.listener.RecipeDetailCallback;
import com.example.team12.components.listener.RecipeFavoriteCallback;
import com.example.team12.components.menu.FragmentIngredientDetailed;
import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.menu.IngredientAdapter;
import com.example.team12.components.menu.IngredientModelClass;
import com.example.team12.components.menu.IngredientModelRedirectInterface;
import com.example.team12.components.menu.RecipeAdapter;
import com.example.team12.components.menu.RecipeModelClass;
import com.example.team12.components.menu.RecipeModelRedirectInterface;
import com.example.team12.entity.Ingredient;
import com.example.team12.entity.IngredientDetail;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.Recipe;
import com.example.team12.entity.RecipeDetail;

import java.util.ArrayList;
import java.util.List;

public class FragmentCategory extends Fragment {
    Toolbar favoriteToolbar;
    TextView categoryName;
    RecyclerView favoriteList;
    ArrayList<RecipeModelClass> favoriteRecipesList;
    ArrayList<IngredientModelClass> favoriteIngredientsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View favoriteView = inflater.inflate(R.layout.fragment_user_favorites, container, false);
        return favoriteView;
    }

    private void addRecipe(int img, List<Recipe> recipeList) {
        for (Recipe recipe: recipeList) {
            RecipeDetail.getRecipeDetailById(recipe.getRecipeId(), new RecipeDetailCallback() {
                @Override
                public void onCallback(RecipeDetail value) {
//                    List<ItemClass> list = new ArrayList<>();
//                    ItemClass newRecipe = new ItemClass(img, recipe.getRecipeName());
//                    newRecipe.ItemInterfaceClick(new ItemInterface() {
//                        @Override
//                        public void onClick(View view, boolean isLongPressed) {
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.frame_layout_main, newRecipe.fragmentRecipeDetailed)
//                                    .commit();
//                        }
//                    });
//                    list.add(newRecipe);
                    RecipeModelClass newMeal = new RecipeModelClass(img, null, recipe.getRecipeName(), value.getCalories() + "kcal");
                    newMeal.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main, FragmentCategory.this);
                    newMeal.RedirectRecipeModel(new RecipeModelRedirectInterface() {
                        @Override
                        public void onClick(View view) {
                            recipe.increaseViewCount();
                            ListVariable.currentRecipe = recipe;
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout_main, newMeal.fragmentRecipeDetailed)
                                    .commit();
                        }
                    });
                    favoriteRecipesList.add(newMeal);
                    RecipeAdapter adapter = new RecipeAdapter(favoriteRecipesList);
                    favoriteList.setLayoutManager(new LinearLayoutManager(getContext()));
                    favoriteList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void addIngredient(int img, List<Ingredient> ingredientList) {
        for (Ingredient ingredient: ingredientList) {
            IngredientDetail.getIngredientDetailById(ingredient.getIngredientId(), new IngredientDetailCallback() {
                @Override
                public void onCallback(IngredientDetail value) {
//                    List<ItemClass> list = new ArrayList<>();
//                    ItemClass newRecipe = new ItemClass(img, recipe.getRecipeName());
//                    newRecipe.ItemInterfaceClick(new ItemInterface() {
//                        @Override
//                        public void onClick(View view, boolean isLongPressed) {
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.frame_layout_main, newRecipe.fragmentRecipeDetailed)
//                                    .commit();
//                        }
//                    });
//                    list.add(newRecipe);
                    Log.i("Ingredient", "onCallback: " + value.getCalories());
                    IngredientModelClass newMeal = new IngredientModelClass(img, null, ingredient.getIngredientName(), value.getCalories() + "kcal");
                    newMeal.fragmentIngredientDetailed = new FragmentIngredientDetailed(R.id.frame_layout_main, FragmentCategory.this);
                    newMeal.RedirectIngredientModel(new IngredientModelRedirectInterface() {
                        @Override
                        public void onClick(View view) {
//                            recipe.increaseViewCount();
                            ListVariable.currentIngredient = ingredient;
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout_main, newMeal.fragmentIngredientDetailed)
                                    .commit();
                        }
                    });
                    favoriteIngredientsList.add(newMeal);
                    IngredientAdapter adapter = new IngredientAdapter(favoriteIngredientsList);
                    favoriteList.setLayoutManager(new LinearLayoutManager(getContext()));
                    favoriteList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteToolbar = view.findViewById(R.id.favorite_toolbar);
        favoriteToolbar.setTitle("Search");

        favoriteList = view.findViewById(R.id.favorite_list_rv);

        categoryName = view.findViewById(R.id.favorite_header);
        categoryName.setText("Category: " + ListVariable.currentCategory.getCategoryName());

        favoriteRecipesList = new ArrayList<>();
        Log.i("Recipe", "onViewCreated: " + ListVariable.currentCategory.getCategoryId());
        if (ListVariable.currentCategory.getCategoryType().equals("Ingredient")) {
            Ingredient.getIngredientByCategory(ListVariable.currentCategory.getCategoryId(), new IngredientCategoryCallback() {
                @Override
                public void onCallback(List<Ingredient> value) {
                    addIngredient(R.drawable.img_example_1, value);
                    Log.i("Ingredient 1", "onCallback: " + value.size());
                }
            });
        } else {
            Recipe.getRecipeByCategory(ListVariable.currentCategory.getCategoryId(), new RecipeCategoryCallback() {
                @Override
                public void onCallback(List<Recipe> value) {
                    addRecipe(R.drawable.img_example_1, value);
                    Log.i("Recipe", "onCallback: " + value.size());
                }
            });
        }
//        Recipe.getRecipeFromFavorite(ListVariable.currentUser.getUserId(), new RecipeFavoriteCallback() {
//            @Override
//            public void onCallback(List<Recipe> value) {
//                addRecipe(R.drawable.img_example_1, value);
//
//            }
//        });


        favoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to fragment search
                FragmentSearch fragment = new FragmentSearch();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_main, fragment)
                        .addToBackStack(null)
                        .commit();
//                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
