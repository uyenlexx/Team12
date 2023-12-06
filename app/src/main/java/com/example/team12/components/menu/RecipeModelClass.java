package com.example.team12.components.menu;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.team12.R;
import com.example.team12.components.home.ItemClass;
import com.example.team12.components.home.MyAdapter;

import java.util.List;

public class RecipeModelClass implements View.OnClickListener {
    int image;
    String header;
    String recipeName;
    String recipeCalories;
    MyAdapter recipesInAMeal;
    private RecipeModelRedirectInterface recipeModelRedirectInterface;
    public FragmentRecipeDetailed fragmentRecipeDetailed;

    public RecipeModelClass(String header, List<ItemClass> listRecipes) {
        this.header = header;
        recipesInAMeal = new MyAdapter(listRecipes);
//        this.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_menu);
    }

    @Override
    public void onClick(View view) {
        System.out.println("reached recipe model class");
//        recipeModelRedirectInterface.onClick(view);
    }

    public void RedirectRecipeModel(RecipeModelRedirectInterface recipeModelRedirectInterface) {
        this.recipeModelRedirectInterface = recipeModelRedirectInterface;
    }
}
