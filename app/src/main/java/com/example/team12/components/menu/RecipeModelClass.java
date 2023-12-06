package com.example.team12.components.menu;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.team12.R;

public class RecipeModelClass implements View.OnClickListener {
    int image;
    String header;
    String recipeName;
    String recipeCalories;
    String url;
    private RecipeModelRedirectInterface recipeModelRedirectInterface;
    public FragmentRecipeDetailed fragmentRecipeDetailed;

    public RecipeModelClass(int image, String header, String recipeName, String recipeCalories) {
        this.image = image;
        this.header = header;
        this.recipeName = recipeName;
        this.recipeCalories = recipeCalories;
        this.url = null;
//        this.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_menu);
    }

    public RecipeModelClass(String url, String header, String recipeName, String recipeCalories) {
        this.url = url;
        this.header = header;
        this.recipeName = recipeName;
        this.recipeCalories = recipeCalories;
    }

    @Override
    public void onClick(View view) {
//        System.out.println("reached recipe model class");
        recipeModelRedirectInterface.onClick(view);
    }

    public void RedirectRecipeModel(RecipeModelRedirectInterface recipeModelRedirectInterface) {
        this.recipeModelRedirectInterface = recipeModelRedirectInterface;
    }

    public String getUrl() {
        return url;
    }
}
