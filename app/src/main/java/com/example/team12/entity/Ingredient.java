package com.example.team12.entity;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Ingredient {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Ingredients");
    private static int totalIngredients = 0;
    private static int maxIngredientId = 0;
    private int ingredientId;
    private String ingredientName;
    private String categoryId;
    private String imageURL;
    private String calories;
    private String description;
    private String userId;

    public Ingredient(int ingredientId, String ingredientName, String categoryId, String imageURL, String calories, String description, String userId) {
        this.ingredientId = maxIngredientId + 1;
        this.ingredientName = ingredientName;
        this.categoryId = categoryId;
        this.imageURL = imageURL;
        this.calories = calories;
        this.description = description;
        this.userId = userId;
    }

    public Ingredient() {
        this.ingredientId = maxIngredientId + 1;
        this.ingredientName = "Temp Ingredient";
        this.categoryId = "0";
        this.imageURL = "imageURL";
        this.calories = "0";
        this.description = "0";
        this.userId = "0";
    }

    // Getters
    public int getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getCalories() {
        return calories;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public static int getTotalIngredients() {
        return totalIngredients;
    }

    public static int getMaxIngredientId() {
        return maxIngredientId;
    }

    // Setters

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public static void setTotalIngredients(int totalIngredients) {
        Ingredient.totalIngredients = totalIngredients;
    }

    public static void setMaxIngredientId(int maxIngredientId) {
        Ingredient.maxIngredientId = maxIngredientId;
    }

    public static void setUpFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalIngredients = (int) dataSnapshot.getChildrenCount();
                maxIngredientId = 0;
                int i = 0;
                ListVariable.ingredientList = Arrays.asList(new Ingredient[totalIngredients]);
                for (DataSnapshot ingredientSnapshot : dataSnapshot.getChildren()) {
                    Ingredient ingredient = ingredientSnapshot.getValue(Ingredient.class);
                    ListVariable.ingredientList.set(i, ingredient);
                    i++;
                    if (ingredient.getIngredientId() > maxIngredientId) {
                        maxIngredientId = ingredient.getIngredientId();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Ingredient setUpFirebase", "onCancelled", databaseError.toException());
            }
        });
    }

    public void saveIngredientToFirebase() {
        reference.child(String.valueOf(this.ingredientName) + " - " + this.ingredientId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("Ingredient saveIngredientToFirebase", "Ingredient does not exist");
                    reference.child(String.valueOf(this.ingredientName) + " - " + this.ingredientId).setValue(this);
                } else {
                    Log.i("Ingredient saveIngredientToFirebase", "Ingredient already exists");
                }
            } else {
                Log.e("Ingredient saveIngredientToFirebase", task.getException().getMessage());
            }
        });
    }
}
