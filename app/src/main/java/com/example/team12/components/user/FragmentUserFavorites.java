package com.example.team12.components.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.components.FragmentUser;
import com.example.team12.components.home.ItemClass;
import com.example.team12.components.home.ItemInterface;
import com.example.team12.components.listener.RecipeDetailCallback;
import com.example.team12.components.listener.RecipeFavoriteCallback;
import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.menu.RecipeAdapter;
import com.example.team12.components.menu.RecipeModelClass;
import com.example.team12.components.menu.RecipeModelRedirectInterface;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.Recipe;
import com.example.team12.entity.RecipeDetail;

import java.util.ArrayList;
import java.util.List;

public class FragmentUserFavorites extends Fragment {
    Toolbar favoriteToolbar;
    RecyclerView favoriteList;
    ArrayList<RecipeModelClass> favoriteRecipesList;

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
                    List<ItemClass> list = new ArrayList<>();
                    ItemClass newRecipe = new ItemClass(img, recipe.getRecipeName());
                    newRecipe.ItemInterfaceClick(new ItemInterface() {
                        @Override
                        public void onClick(View view, boolean isLongPressed) {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout_main, newRecipe.fragmentRecipeDetailed)
                                    .commit();
                        }
                    });
                    list.add(newRecipe);
                    RecipeModelClass newMeal = new RecipeModelClass(null, list);
//                    newRecipe.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_user, FragmentUserFavorites.this);
//                    newMeal.RedirectRecipeModel(new RecipeModelRedirectInterface() {
//                        @Override
//                        public void onClick(View view) {
//                            recipe.increaseViewCount();
//                            ListVariable.currentRecipe = recipe;
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.frame_layout_user, newRecipe.fragmentRecipeDetailed)
//                                    .commit();
//                        }
//                    });
                    favoriteRecipesList.add(newMeal);
                    RecipeAdapter adapter = new RecipeAdapter(favoriteRecipesList);
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
        favoriteList = view.findViewById(R.id.favorite_list_rv);
        favoriteRecipesList = new ArrayList<>();
        Recipe.getRecipeFromFavorite(ListVariable.currentUser.getUserId(), new RecipeFavoriteCallback() {
            @Override
            public void onCallback(List<Recipe> value) {
                    addRecipe(R.drawable.img_example_1, value);

            }
        });

//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_1), "Breakfast", "Recipe 1", "500kcal", this));
//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_2), "Lunch", "Recipe 2", "500kcal", this));
//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_3), "Dinner", "Recipe 3", "500kcal", this));
//        addRecipe(R.drawable.img_example_1, "Breakfast", "Recipe 1", "500kcal");
//        addRecipe(R.drawable.img_example_2, "Lunch", "Recipe 2", "500kcal");
//        addRecipe(R.drawable.img_example_3, "Dinner", "Recipe 3", "500kcal");
//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_1), "", "Recipe 1", "500kcal"));
//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_2), "", "Recipe 2", "500kcal"));
//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_3), "", "Recipe 3", "500kcal"));


        favoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
