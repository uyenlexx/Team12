package com.example.team12.entity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IngredientDetail {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("IngredientDetails");
    private int ingredientDetailId;
    private int unit;
    private float protein;
    private float calories;
    private float fat;

    public IngredientDetail(int ingredientDetailId, int unit, float protein, float calories, float fat) {
        this.ingredientDetailId = ingredientDetailId;
        this.unit = unit;
        this.protein = protein;
        this.calories = calories;
        this.fat = fat;
    }

    public IngredientDetail() {
        this.ingredientDetailId = 0;
        this.unit = 0;
        this.protein = 0;
        this.calories = 0;
        this.fat = 0;
    }

    // Getters
    public int getIngredientDetailId() {
        return ingredientDetailId;
    }

    public int getUnit() {
        return unit;
    }

    public float getProtein() {
        return protein;
    }

    public float getCalories() {
        return calories;
    }

    public float getFat() {
        return fat;
    }

    // Setters

    public void setIngredientDetailId(int ingredientDetailId) {
        this.ingredientDetailId = ingredientDetailId;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public static void getIngredientDetail(int ingredientId) {
        reference.orderByChild("ingredientDetailId").equalTo(ingredientId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    IngredientDetail ingredientDetail = snapshot.getValue(IngredientDetail.class);
                    if (ingredientDetail != null) {
                        //Check if Value already exists
                        boolean exists = false;
                        for (IngredientDetail ingredientDetail1 : ListVariable.ingredientDetailList) {
                            if (ingredientDetail1.getIngredientDetailId() == ingredientDetail.getIngredientDetailId()) {
                                if (ingredientDetail1 == ingredientDetail) {
                                    exists = true;
                                    break;
                                }
                                else {
                                    ListVariable.ingredientDetailList.remove(ingredientDetail1);
                                    ListVariable.ingredientDetailList.add(ingredientDetail);
                                    exists = true;
                                    break;
                                }
                            }
                        }
                        if (!exists) {
                            ListVariable.ingredientDetailList.add(ingredientDetail);
                        }
                    }
                }
            }
        });
    }

    public void saveIngredientDetailToFirebase() {
        reference.child(String.valueOf(this.ingredientDetailId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    reference.child(String.valueOf(this.ingredientDetailId)).setValue(this);
                }
            }
        });
    }
}
