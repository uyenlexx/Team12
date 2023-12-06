package com.example.team12.entity;

import android.view.View;

import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.search.IngredientListRedirectInterface;

public class IngredientList implements View.OnClickListener {
    public String url;
    public String description;
    public String name;
    private IngredientListRedirectInterface ingredientListRedirectInterface;
    public FragmentRecipeDetailed recipeDetailed;
    public IngredientList(String name, String description, String url) {
        this.description = description;
        this.name = name;
        this.url = url;
    }

    public IngredientList(){

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }


    @Override
    public void onClick(View view) {
        ingredientListRedirectInterface.onClick(view);
    }

    public void setIngredientListRedirectInterface(IngredientListRedirectInterface ingredientListRedirectInterface) {
        this.ingredientListRedirectInterface = ingredientListRedirectInterface;
    }
}
