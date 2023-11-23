package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecipeIngredient {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("RecipeIngredients");
    private static int totalRecipeIngredient = 0;
    private static int maxRecipeIngredientId = 0;
    private int recipeIngredientID;
    private int recipeID;
    private int ingredientID;

    public RecipeIngredient(int recipeIngredientID, int recipeID, int ingredientID) {
        this.recipeIngredientID = maxRecipeIngredientId + 1;
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
    }

    public RecipeIngredient() {
        this.recipeIngredientID = maxRecipeIngredientId + 1;
        this.recipeID = 0;
        this.ingredientID = 0;
    }

    // Getters

    public int getRecipeIngredientID() {
        return recipeIngredientID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public static int getTotalRecipeIngredient() {
        return totalRecipeIngredient;
    }

    public static int getMaxRecipeIngredientId() {
        return maxRecipeIngredientId;
    }

    // Setters

    public void setRecipeIngredientID(int recipeIngredientID) {
        this.recipeIngredientID = recipeIngredientID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public static void setTotalRecipeIngredient(int totalRecipeIngredient) {
        RecipeIngredient.totalRecipeIngredient = totalRecipeIngredient;
    }

    public static void setMaxRecipeIngredientId(int maxRecipeIngredientId) {
        RecipeIngredient.maxRecipeIngredientId = maxRecipeIngredientId;
    }

    public static void setUpFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalRecipeIngredient = (int) snapshot.getChildrenCount();
                maxRecipeIngredientId = 0;
                int i = 0;
                ListVariable.recipeIngredientList = new RecipeIngredient[totalRecipeIngredient];
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecipeIngredient recipeIngredient = dataSnapshot.getValue(RecipeIngredient.class);
                    ListVariable.recipeIngredientList[i] = recipeIngredient;
                    i++;
                    if (recipeIngredient.getRecipeIngredientID() > maxRecipeIngredientId) {
                        maxRecipeIngredientId = recipeIngredient.getRecipeIngredientID();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Recipe getTotalRecipesFromFirebase", error.getMessage());
            }
        });
    }

    public void saveRecipeIngredientToFirebase() {
        reference.child(String.valueOf(this.recipeIngredientID)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("Recipe saveRecipeToFirebase", "Recipe does not exist");
                    reference.child(String.valueOf(this.recipeIngredientID)).setValue(this);
                } else {
                    Log.i("Recipe saveRecipeToFirebase", "Recipe exists");
                }
            } else {
                Log.e("Recipe saveRecipeToFirebase", task.getException().getMessage());
            }
        });
    }
}
