package com.example.team12.entity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class ListVariable {
    public static TreeMap<String, Recipe> recipeList = new TreeMap<>();
    public static List<RecipeDetail> recipeDetailList = new ArrayList<>();
    public static HashMap<String, Ingredient> ingredientList = new HashMap<>();
    public static List<IngredientDetail> ingredientDetailList = new ArrayList<>();
    public static List<MealPlan> mealPlanList = new ArrayList<>();
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Recipe> trendingRecipeList = new ArrayList<>();
    public static User currentUser = null;

    public static Recipe currentRecipe = null;
    public static Ingredient currentIngredient = null;

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();

}
