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
import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.menu.RecipeAdapter;
import com.example.team12.components.menu.RecipeModelClass;
import com.example.team12.components.menu.RecipeModelRedirectInterface;

import java.util.ArrayList;

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

    private void addRecipe(int img, String header, String recipeName, String recipeCalories) {
        RecipeModelClass newRecipe = new RecipeModelClass(img, header, recipeName, recipeCalories);
        newRecipe.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_user, this);
        newRecipe.RedirectRecipeModel(new RecipeModelRedirectInterface() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, newRecipe.fragmentRecipeDetailed)
                        .commit();
            }
        });
        favoriteRecipesList.add(newRecipe);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteToolbar = view.findViewById(R.id.favorite_toolbar);
        favoriteList = view.findViewById(R.id.favorite_list_rv);
        favoriteRecipesList = new ArrayList<>();

//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_1), "Breakfast", "Recipe 1", "500kcal", this));
//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_2), "Lunch", "Recipe 2", "500kcal", this));
//        favoriteRecipesList.add(new RecipeModelClass((R.drawable.img_example_3), "Dinner", "Recipe 3", "500kcal", this));
        addRecipe(R.drawable.img_example_1, "Breakfast", "Recipe 1", "500kcal");
        addRecipe(R.drawable.img_example_2, "Lunch", "Recipe 2", "500kcal");
        addRecipe(R.drawable.img_example_3, "Dinner", "Recipe 3", "500kcal");

        RecipeAdapter adapter = new RecipeAdapter(favoriteRecipesList);
        favoriteList.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
