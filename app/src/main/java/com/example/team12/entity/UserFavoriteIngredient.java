package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFavoriteIngredient {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UserFavoriteIngredients");
    public static int totalUserFavoriteIngredients = 0;
    public static int maxUserFavoriteIngredientId = 0;
    private int userFavoriteIngredientId;
    private int userId;
    private int ingredientId;

    public UserFavoriteIngredient(int userFavoriteIngredientId, int userId, int ingredientId) {
        this.userFavoriteIngredientId = maxUserFavoriteIngredientId + 1;
        this.userId = userId;
        this.ingredientId = ingredientId;
    }

    public UserFavoriteIngredient() {
        this.userFavoriteIngredientId = maxUserFavoriteIngredientId + 1;
        this.userId = 0;
        this.ingredientId = 0;
    }

    // Getters

    public int getUserFavoriteIngredientId() {
        return userFavoriteIngredientId;
    }

    public int getUserId() {
        return userId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public static int getTotalUserFavoriteIngredients() {
        return totalUserFavoriteIngredients;
    }

    public static int getMaxUserFavoriteIngredientId() {
        return maxUserFavoriteIngredientId;
    }

    // Setters

    public void setUserFavoriteIngredientId(int userFavoriteIngredientId) {
        this.userFavoriteIngredientId = userFavoriteIngredientId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public static void setTotalUserFavoriteIngredients(int totalUserFavoriteIngredients) {
        UserFavoriteIngredient.totalUserFavoriteIngredients = totalUserFavoriteIngredients;
    }

    public static void setMaxUserFavoriteIngredientId(int maxUserFavoriteIngredientId) {
        UserFavoriteIngredient.maxUserFavoriteIngredientId = maxUserFavoriteIngredientId;
    }

    public static void setUpFirebase() {
//        addTempRecipeToFirebase();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalUserFavoriteIngredients = (int) snapshot.getChildrenCount();
                maxUserFavoriteIngredientId = 0;
                int i = 0;
                ListVariable.userFavoriteIngredientList = new UserFavoriteIngredient[totalUserFavoriteIngredients];
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserFavoriteIngredient userFavoriteIngredient = dataSnapshot.getValue(UserFavoriteIngredient.class);
                    ListVariable.userFavoriteIngredientList[i] = userFavoriteIngredient;
                    i++;
                    if (userFavoriteIngredient.getUserFavoriteIngredientId() > maxUserFavoriteIngredientId) {
                        maxUserFavoriteIngredientId = userFavoriteIngredient.getUserFavoriteIngredientId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Recipe getTotalRecipesFromFirebase", error.getMessage());
            }
        });
    }

    public void saveUserFavoriteIngredientToFirebase() {
        reference.child(String.valueOf(this.userFavoriteIngredientId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("Recipe saveRecipeToFirebase", "Recipe does not exist");
                    reference.child(String.valueOf(this.userFavoriteIngredientId)).setValue(this);
                } else {
                    Log.i("Recipe saveRecipeToFirebase", "Recipe exists");
                }
            } else {
                Log.e("Recipe saveRecipeToFirebase", task.getException().getMessage());
            }
        });
    }
}
