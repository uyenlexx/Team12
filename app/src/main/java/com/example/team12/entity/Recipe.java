package com.example.team12.entity;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Recipe {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recipes");
    private static int totalRecipes = 0;
    private static int maxRecipeId = 0;
    private int recipeId;
    private String recipeName;
    private String imageURL;
    private int categoryId;
    private String description;
    private int userId;
    private int viewCount;

    //Init
    public Recipe(String recipeName, String imageURL, int categoryId, String description, int userId) {
        this.recipeId = maxRecipeId + 1;
        this.recipeName = recipeName;
        this.imageURL = imageURL;
        this.categoryId = categoryId;
        this.description = description;
        this.userId = userId;
    }

    public Recipe() {
        this.recipeId = maxRecipeId + 1;
        this.recipeName = "Temp Recipe";
        this.imageURL = "imageURL";
        this.categoryId = 0;
        this.description = "description";
        this.userId = 0;
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

    public int getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }

    public int getViewCount() {
        return viewCount;
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

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public static void setUpFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalRecipes = (int) snapshot.getChildrenCount();
                maxRecipeId = 0;
                ListVariable.recipeList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
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

//    public CompletableFuture<Void> getTotalRecipe() {
//        CompletableFuture<Void> future = new CompletableFuture<>();
//        reference.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                maxRecipeId = 0;
//                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
//                    Recipe recipe = new Recipe();
//                    recipe.setRecipeName(dataSnapshot.child("recipeName").getValue(String.class));
//                    recipe.setRecipeId(dataSnapshot.child("recipeId").getValue(Integer.class));
//                    recipe.setImageURL(dataSnapshot.child("imageURL").getValue(String.class));
//                    recipe.setCategoryId(dataSnapshot.child("categoryId").getValue(Integer.class));
//                    recipe.setProtein(dataSnapshot.child("protein").getValue(Integer.class));
//                    recipe.setCalories(dataSnapshot.child("calories").getValue(Integer.class));
//                    recipe.setFat(dataSnapshot.child("fat").getValue(Integer.class));
//                    recipe.setCarbs(dataSnapshot.child("carbs").getValue(Integer.class));
//                    recipe.setDescription(dataSnapshot.child("description").getValue(String.class));
//                    recipe.setSteps(dataSnapshot.child("steps").getValue(String.class));
//                    recipe.setUserId(dataSnapshot.child("userId").getValue(Integer.class));
//                    recipe.setViewCount(dataSnapshot.child("viewCount").getValue(Integer.class));
//                    List<Pair<Integer, Integer>> tempingredientList = new ArrayList<>();
//                    for (DataSnapshot ingredientSnapshot : dataSnapshot.child("Ingredient").getChildren()) {
//                        Pair<Integer, Integer> ingredient = new Pair<>(Integer.parseInt(ingredientSnapshot.getKey()), ingredientSnapshot.getValue(Integer.class));
//                        tempingredientList.add(ingredient);
//                    }
//                    recipe.setIngredientList(tempingredientList);
//                    ListVariable.recipeList.add(recipe);
//                    if (recipe.getRecipeId() > maxRecipeId) {
//                        maxRecipeId = recipe.getRecipeId();
//                    }
//                }
//                totalRecipes = (int) task.getResult().getChildrenCount();
//                Log.i("Recipe getTotalRecipe", "Total recipe: " + totalRecipes);
//                future.complete(null);
//            } else {
//                Log.e("Recipe getTotalRecipe", task.getException().getMessage());
//                future.completeExceptionally(task.getException());
//            }
//        });
//        return future;
//    }

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
        reference.orderByChild("recipeId").equalTo(3).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().getChildren().forEach(recipe -> {
                    Log.i("Recipe getAllRecipeIngredient", recipe.child("recipeName").getValue(String.class));
                    recipe.child("Ingredient").getChildren().forEach(ingredient -> {
                        Log.i("Recipe getAllRecipeIngredient", ingredient.getKey());
                    });
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
//    public void saveRecipeToFirebase() {
//        CompletableFuture<Void> future = getTotalRecipe();
//
//        future.thenRun(this::saveRecipe);
//    }

    public void onUserClicked() {
        Map<String, Object> updates = new HashMap<>();
        updates.put(this.recipeName + " - " + this.recipeId + "/viewCount", ServerValue.increment(1));
        reference.updateChildren(updates);
    }

    public void getRecipeByViewCount() {
        reference.orderByChild("viewCount").limitToLast(10).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ListVariable.trendingRecipeList.clear();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    ListVariable.recipeList.add(recipe);
                }
            } else {
                Log.e("Recipe getRecipeByViewCount", task.getException().getMessage());
            }
        });
    }
}
