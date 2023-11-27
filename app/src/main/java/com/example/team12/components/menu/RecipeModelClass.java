package com.example.team12.components.menu;

public class RecipeModelClass {
    int image;
    String header;
    String recipeName;
    String recipeCalories;

    public RecipeModelClass(int image, String header, String recipeName, String recipeCalories) {
        this.image = image;
        this.header = header;
        this.recipeName = recipeName;
        this.recipeCalories = recipeCalories;
    }
}
