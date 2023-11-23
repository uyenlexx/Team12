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

    public Recipe(int recipeId, String recipeName, String imageURL, String categoryId, String calories, String description, String steps, String adminId) {
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

    public static void setUpFirebase() {
//        addTempRecipeToFirebase();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalRecipes = (int) snapshot.getChildrenCount();
                maxRecipeId = 0;
                int i = 0;
                ListVariable.recipeList = new Recipe[totalRecipes];
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    ListVariable.recipeList[i] = recipe;
                    i++;
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

    public void saveRecipeToFirebase() {
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
}
