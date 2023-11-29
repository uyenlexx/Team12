package com.example.team12.entity;

import java.util.ArrayList;
import java.util.List;

public class ListVariable {
    public static List<Recipe> recipeList = new ArrayList<>();
    public static List<Ingredient> ingredientList = new ArrayList<>();
    public static List<MealPlan> mealPlanList = new ArrayList<>();
    public static List<Category> categoryList = new ArrayList<>();
    public static RecipeIngredient[] recipeIngredientList = new RecipeIngredient[100];
    public static UserFavoriteIngredient[] userFavoriteIngredientList = new UserFavoriteIngredient[100];
    public static UserFavoriteRecipe[] userFavoriteRecipeList = new UserFavoriteRecipe[100];
}
