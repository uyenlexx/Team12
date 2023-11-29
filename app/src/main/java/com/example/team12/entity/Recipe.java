package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.team12.components.listener.RecipeDetailCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recipes");
    public static int totalRecipes = 0;
    public static int maxRecipeId = 0;
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
        this.viewCount = 0;
    }

    public Recipe() {
        this.recipeId = maxRecipeId + 1;
        this.recipeName = "Temp Recipe";
        this.imageURL = "imageURL";
        this.categoryId = 0;
        this.description = "description";
        this.userId = 0;
        this.viewCount = 0;
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
                //Sort recipe list by view count in descending order
                Collections.sort(ListVariable.recipeList, (o1, o2) -> o2.getViewCount() - o1.getViewCount());
                Log.i("Recipe setUpFirebase", "Total recipe: " + totalRecipes + " - Max recipe id: " + maxRecipeId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Recipe getTotalRecipesFromFirebase", error.getMessage());
            }
        });
    }

    public void saveRecipe() {
        Log.i("Recipe saveRecipeToFirebase", "Total recipe: " + totalRecipes + " - Max recipe id: " + maxRecipeId);
        this.recipeId = maxRecipeId + 1;
        reference.child("" + this.recipeId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("Recipe saveRecipeToFirebase", "Recipe does not exist");
                    reference.child("" + this.recipeId).setValue(this);
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

    public void increaseViewCount() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("" + this.recipeId + "/viewCount", ServerValue.increment(1));
        reference.updateChildren(updates);
    }

    public static void getRecipeByViewCount() {
        reference.orderByChild("viewCount").limitToLast(5).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ListVariable.trendingRecipeList.clear();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    ListVariable.trendingRecipeList.add(recipe);
                }
            } else {
                Log.e("Recipe getRecipeByViewCount", task.getException().getMessage());
            }
        });
        for (Recipe recipe : ListVariable.trendingRecipeList) {
            Log.i("Recipe getRecipeByViewCount", recipe.toString());
        }
        Log.i("Recipe getRecipeByViewCount", "Total trending recipe: " + ListVariable.trendingRecipeList.size());
        Log.i("Recipe getRecipeByViewCount", "Total recipe: " + ListVariable.recipeList.size());
    }

    public static List<Recipe> getRecipeByCategory(int _categoryId) {
        List<Recipe> recipeList = new ArrayList<>();
        reference.orderByChild("categoryId").equalTo(_categoryId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
            } else {
                Log.e("Recipe getRecipeByCategory", task.getException().getMessage());
            }
        });
        return recipeList;
    }

    public static List<Recipe> getRecipeByUserId(int _userId) {
        List<Recipe> recipeList = new ArrayList<>();
        reference.orderByChild("userId").equalTo(_userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
            } else {
                Log.e("Recipe getRecipeByUserId", task.getException().getMessage());
            }
        });
        return recipeList;
    }

    public void updateRecipeToFirebase() {
        reference.child("" + this.recipeId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("recipeName", this.recipeName);
                    hashMap.put("imageURL", this.imageURL);
                    hashMap.put("categoryId", this.categoryId);
                    hashMap.put("description", this.description);
                    reference.child("" + this.recipeId).updateChildren(hashMap).addOnSuccessListener(aVoid -> {
                        Log.i("Recipe updateRecipeToFirebase", "Recipe updated");
                    }).addOnFailureListener(e -> {
                        Log.e("Recipe updateRecipeToFirebase", e.getMessage());
                    });
                } else {
                    Log.i("Recipe updateRecipeToFirebase", "Recipe does not exist");
                }
            } else {
                Log.e("Recipe updateRecipeToFirebase", task.getException().getMessage());
            }
        });
    }

    public static void removeRecipeFromFirebase(int recipeId) {
        reference.child("" + recipeId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    reference.child("" + recipeId).removeValue().addOnSuccessListener(aVoid -> {
                        Log.i("Recipe removeRecipeFromFirebase", "Recipe removed successfully");
                    }).addOnFailureListener(e -> {
                        Log.e("Recipe removeRecipeFromFirebase", e.getMessage());
                    });
                } else {
                    Log.i("Recipe removeRecipeFromFirebase", "Recipe does not exist");
                }
            } else {
                Log.e("Recipe removeRecipeFromFirebase", task.getException().getMessage());
            }
        });
        RecipeDetail.removeRecipeDetailFromFirebase(recipeId);
    }

    @NonNull
    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", recipeName='" + recipeName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }

    public static void getRecipeFromFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListVariable.recipeList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    ListVariable.recipeList.add(recipe);
                }
                Collections.sort(ListVariable.recipeList, (o1, o2) -> o2.getViewCount() - o1.getViewCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Recipe getRecipeFromFirebase", error.getMessage());
            }
        });
    }
}

