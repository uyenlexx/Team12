package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class Recipe {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recipes");
    private static int totalRecipes = 0;
    private static int maxRecipeId = 0;
    private int recipeId;
    private String recipeName;
    private String imageURL;
    private String categoryId;
    private String calories;
    private String description;
    private String steps;
    private String adminId;
    private String[] ingredient;
    public Recipe(int recipeId, String recipeName, String imageURL, String categoryId, String calories, String description, String steps, String adminId, String[] ingredient) {
        this.recipeId = maxRecipeId + 1;
        this.recipeName = recipeName;
        this.imageURL = imageURL;
        this.categoryId = categoryId;
        this.calories = calories;
        this.description = description;
        this.steps = steps;
        this.adminId = adminId;
    }

    public Recipe() {
        this.recipeId = maxRecipeId + 1;
        this.recipeName = "Temp Recipe";
        this.imageURL = "imageURL";
        this.categoryId = "0";
        this.calories = "0";
        this.description = "0";
        this.steps = "0";
        this.adminId = "0";
    }

    // Getters
    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCalories() {
        return calories;
    }

    public String getDescription() {
        return description;
    }

    public String getSteps() {
        return steps;
    }

    public String getAdminId() {
        return adminId;
    }

    public String[] getIngredient() {
        return ingredient;
    }

    // Setters
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setIngredient(String[] ingredient) {
        this.ingredient = ingredient;
    }
    public static void setUpFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalRecipes = (int) snapshot.getChildrenCount();
                maxRecipeId = 0;
                ListVariable.recipeList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeName(dataSnapshot.child("recipeName").getValue(String.class));
                    recipe.setRecipeId(dataSnapshot.child("recipeId").getValue(Integer.class));
                    recipe.setImageURL(dataSnapshot.child("imageURL").getValue(String.class));
                    recipe.setCategoryId(dataSnapshot.child("categoryId").getValue(String.class));
                    recipe.setCalories(dataSnapshot.child("calories").getValue(String.class));
                    recipe.setDescription(dataSnapshot.child("description").getValue(String.class));
                    recipe.setSteps(dataSnapshot.child("steps").getValue(String.class));
                    recipe.setAdminId(dataSnapshot.child("adminId").getValue(String.class));
                    String[] ingredient = new String[(int) dataSnapshot.child("Ingredient").getChildrenCount()];
                    int i = 0;
                    for (DataSnapshot ingredientSnapshot : dataSnapshot.child("Ingredient").getChildren()) {
                        ingredient[i] = ingredientSnapshot.getValue(String.class);
                        i++;
                    }
                    recipe.setIngredient(ingredient);
                    ListVariable.recipeList.add(recipe);
                    if (recipe.getRecipeId() > maxRecipeId) {
                        maxRecipeId = recipe.getRecipeId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Recipe getTotalRecipesFromFirebase", error.getMessage());
            }
        });
    }

    public CompletableFuture<Void> getTotalRecipe() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                maxRecipeId = 0;
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    String key = dataSnapshot.getKey();
                    Recipe recipe = new Recipe();
                    recipe.setRecipeName(dataSnapshot.child("recipeName").getValue(String.class));
                    recipe.setRecipeId(dataSnapshot.child("recipeId").getValue(Integer.class));
                    recipe.setImageURL(dataSnapshot.child("imageURL").getValue(String.class));
                    recipe.setCategoryId(dataSnapshot.child("categoryId").getValue(String.class));
                    recipe.setCalories(dataSnapshot.child("calories").getValue(String.class));
                    recipe.setDescription(dataSnapshot.child("description").getValue(String.class));
                    recipe.setSteps(dataSnapshot.child("steps").getValue(String.class));
                    recipe.setAdminId(dataSnapshot.child("adminId").getValue(String.class));
                    String[] ingredient = new String[(int) dataSnapshot.child("Ingredient").getChildrenCount()];
                    int i = 0;
                    for (DataSnapshot ingredientSnapshot : dataSnapshot.child("Ingredient").getChildren()) {
                        ingredient[i] = ingredientSnapshot.getValue(String.class);
                        i++;
                    }
                    recipe.setIngredient(ingredient);
                    ListVariable.recipeList.add(recipe);
                    if (recipe.getRecipeId() > maxRecipeId) {
                        maxRecipeId = recipe.getRecipeId();
                    }
                }
                totalRecipes = (int) task.getResult().getChildrenCount();
                Log.i("Recipe getTotalRecipe", "Total recipe: " + totalRecipes);
                future.complete(null);
            } else {
                Log.e("Recipe getTotalRecipe", task.getException().getMessage());
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    public void getAllRecipeIngredient() {
        reference.child("name1 - 0").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Get all ingredient, ingredient is a list
                task.getResult().child("Ingredient").getChildren().forEach(ingredient -> {
                    Log.i("Recipe getAllRecipeIngredient", ingredient.getKey());
                });

            } else {
                Log.e("Recipe getAllRecipeIngredient", task.getException().getMessage());
            }
        });
    }

    public void saveRecipe() {
        Log.i("Recipe saveRecipeToFirebase", "Total recipe: " + totalRecipes + " - Max recipe id: " + maxRecipeId);
        this.recipeId = maxRecipeId + 1;
        reference.child(String.valueOf(this.recipeName) + " - " + this.recipeId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("Recipe saveRecipeToFirebase", "Recipe does not exist");
                    reference.child(String.valueOf(this.recipeName) + " - " + this.recipeId).setValue(this);
                } else {
                    Log.i("Recipe saveRecipeToFirebase", "Recipe exists");
                }
            } else {
                Log.e("Recipe saveRecipeToFirebase", task.getException().getMessage());
            }
        });
    }
    public void saveRecipeToFirebase() {
        CompletableFuture<Void> future = getTotalRecipe();

        future.thenRun(this::saveRecipe);
    }
}
