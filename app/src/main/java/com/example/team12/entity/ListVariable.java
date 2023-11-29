package com.example.team12.entity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ListVariable {
    public static List<Recipe> recipeList = new ArrayList<>();
    public static List<RecipeDetail> recipeDetailList = new ArrayList<>();
    public static List<Ingredient> ingredientList = new ArrayList<>();
    public static List<IngredientDetail> ingredientDetailList = new ArrayList<>();
    public static List<MealPlan> mealPlanList = new ArrayList<>();
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Recipe> trendingRecipeList = new ArrayList<>();
    public static User currentUser = null;

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
}
