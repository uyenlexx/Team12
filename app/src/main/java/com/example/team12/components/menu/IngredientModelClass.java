package com.example.team12.components.menu;

import android.view.View;

public class IngredientModelClass implements View.OnClickListener{
    int image;
    String header;
    String ingredientName;
    String ingredientCalories;
    String url;
    private IngredientModelRedirectInterface ingredientModelRedirectInterface;
    public FragmentIngredientDetailed fragmentIngredientDetailed;

    public IngredientModelClass(int image, String header, String recipeName, String recipeCalories) {
        this.image = image;
        this.header = header;
        this.ingredientName = recipeName;
        this.ingredientCalories = recipeCalories;
        this.url = null;
//        this.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_menu);
    }

    public IngredientModelClass(String url, String header, String recipeName, String recipeCalories) {
        this.url = url;
        this.header = header;
        this.ingredientName = recipeName;
        this.ingredientCalories = recipeCalories;
    }

    @Override
    public void onClick(View view) {
//        System.out.println("reached recipe model class");
        ingredientModelRedirectInterface.onClick(view);
    }

    public void RedirectIngredientModel(IngredientModelRedirectInterface ingredientModelRedirectInterface) {
        this.ingredientModelRedirectInterface = ingredientModelRedirectInterface;
    }

    public String getUrl() {
        return url;
    }
}
