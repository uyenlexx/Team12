package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFavoriteRecipe {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UserFavoriteRecipes");
    public static int totalUserFavoriteRecipes = 0;
    public static int maxUserFavoriteRecipeId = 0;
    private int userFavoriteRecipeId;
    private int userId;
    private int recipeId;

    public UserFavoriteRecipe(int userFavoriteRecipeId, int userId, int recipeId) {
        this.userFavoriteRecipeId = maxUserFavoriteRecipeId + 1;
        this.userId = userId;
        this.recipeId = recipeId;
    }

    public UserFavoriteRecipe() {
        this.userFavoriteRecipeId = maxUserFavoriteRecipeId + 1;
        this.userId = 0;
        this.recipeId = 0;
    }

    // Getters

    public int getUserFavoriteRecipeId() {
        return userFavoriteRecipeId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public static int getTotalUserFavoriteRecipes() {
        return totalUserFavoriteRecipes;
    }

    public static int getMaxUserFavoriteRecipeId() {
        return maxUserFavoriteRecipeId;
    }

    // Setters

    public void setUserFavoriteRecipeId(int userFavoriteRecipeId) {
        this.userFavoriteRecipeId = userFavoriteRecipeId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public static DatabaseReference getReference() {
        return reference;
    }

    public static void setReference(DatabaseReference reference) {
        UserFavoriteRecipe.reference = reference;
    }

    public static void setTotalUserFavoriteRecipes(int totalUserFavoriteRecipes) {
        UserFavoriteRecipe.totalUserFavoriteRecipes = totalUserFavoriteRecipes;
    }

    public static void setMaxUserFavoriteRecipeId(int maxUserFavoriteRecipeId) {
        UserFavoriteRecipe.maxUserFavoriteRecipeId = maxUserFavoriteRecipeId;
    }

    public static void setUpFirebase() {
//        addTempRecipeToFirebase();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalUserFavoriteRecipes = (int) snapshot.getChildrenCount();
                maxUserFavoriteRecipeId = 0;
                int i = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserFavoriteRecipe userFavoriteRecipe = dataSnapshot.getValue(UserFavoriteRecipe.class);
                    if (userFavoriteRecipe.getUserFavoriteRecipeId() > maxUserFavoriteRecipeId) {
                        ListVariable.userFavoriteRecipeList[i] = userFavoriteRecipe;
                        maxUserFavoriteRecipeId = userFavoriteRecipe.getUserFavoriteRecipeId();
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Recipe getTotalRecipesFromFirebase", error.getMessage());
            }
        });
    }

    public void saveUserToFirebase() {
        reference.child(String.valueOf(this.userFavoriteRecipeId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("UserFavoriteRecipe saveUserToFirebase", "UserFavoriteRecipe does not exist");
                    reference.child(String.valueOf(this.userFavoriteRecipeId)).setValue(this);
                } else {
                    Log.i("UserFavoriteRecipe saveUserToFirebase", "UserFavoriteRecipe exists");
                }
            } else {
                Log.e("UserFavoriteRecipe saveUserToFirebase", task.getException().getMessage());
            }
        });
    }
}
