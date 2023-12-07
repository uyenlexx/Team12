package com.example.team12.entity;

import android.util.Log;

import com.example.team12.components.listener.IngredientDetailCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class IngredientDetail {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("IngredientDetails");
    private int ingredientDetailId;
    private String unit;
    private float protein;
    private float calories;
    private float fat;
    private float carbs;
    private String description;

    //Init
    public IngredientDetail(int ingredientDetailId, String unit, float protein, float calories, float fat, float carbs, String description) {
        this.ingredientDetailId = ingredientDetailId;
        this.unit = unit;
        this.protein = protein;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.description = description;
    }

    public IngredientDetail() {
        this.ingredientDetailId = 0;
        this.unit = "";
        this.protein = 0;
        this.calories = 0;
        this.fat = 0;
        this.carbs = 0;
        this.description = "";
    }

    // Getters
    public int getIngredientDetailId() {
        return ingredientDetailId;
    }

    public String getUnit() {
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

    public float getCarbs() {
        return carbs;
    }

    public String getDescription() {
        return description;
    }

    // Setters

    public void setIngredientDetailId(int ingredientDetailId) {
        this.ingredientDetailId = ingredientDetailId;
    }

    public void setUnit(String unit) {
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

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void getIngredientDetailById(int ingredientId, IngredientDetailCallback callback) {
        reference.orderByKey().equalTo(String.valueOf(ingredientId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    IngredientDetail ingredientDetail = new IngredientDetail();
                    ingredientDetail.setIngredientDetailId(Integer.parseInt(snapshot.getKey()));
                    ingredientDetail.setUnit(snapshot.child("unit").getValue(String.class));
                    ingredientDetail.setProtein(snapshot.child("protein").getValue(Float.class));
                    ingredientDetail.setCalories(snapshot.child("calories").getValue(Float.class));
                    ingredientDetail.setFat(snapshot.child("fat").getValue(Float.class));
                    ingredientDetail.setCarbs(snapshot.child("carbs").getValue(Float.class));
                    ingredientDetail.setDescription(snapshot.child("description").getValue(String.class));
                    //Check if ingredient detail is already in the list
                    boolean isExist = false;
                    for (IngredientDetail ingredientDetail1 : ListVariable.ingredientDetailList) {
                        if (ingredientDetail1.getIngredientDetailId() == ingredientDetail.getIngredientDetailId()) {
                            if (ingredientDetail1 == ingredientDetail) {
                                isExist = true;
                            } else {
                                ingredientDetail1.setUnit(ingredientDetail.getUnit());
                                ingredientDetail1.setProtein(ingredientDetail.getProtein());
                                ingredientDetail1.setCalories(ingredientDetail.getCalories());
                                ingredientDetail1.setFat(ingredientDetail.getFat());
                                ingredientDetail1.setCarbs(ingredientDetail.getCarbs());
                                ingredientDetail1.setDescription(ingredientDetail.getDescription());
                                isExist = true;
                            }
                        }
                    }
                    if (!isExist) {
                        ListVariable.ingredientDetailList.add(ingredientDetail);
                    }
                    callback.onCallback(ingredientDetail);
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

    public void updateIngredientDetailToFirebase() {
        reference.child(String.valueOf(this.ingredientDetailId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    HashMap<String, Object> updateMap = new HashMap<>();
                    updateMap.put("unit", this.unit);
                    updateMap.put("protein", this.protein);
                    updateMap.put("calories", this.calories);
                    updateMap.put("fat", this.fat);
                    updateMap.put("carbs", this.carbs);
                    updateMap.put("description", this.description);
                    reference.child(String.valueOf(this.ingredientDetailId)).updateChildren(updateMap).addOnSuccessListener(aVoid -> {
                        Log.i("IngredientDetail updateIngredientDetailToFirebase", "IngredientDetail updated");
                    }).addOnFailureListener(e -> {
                        Log.e("IngredientDetail updateIngredientDetailToFirebase", e.getMessage());
                    });
                }
            }
        });
    }

    public static void removeIngredientDetailFromFirebase(int ingredientDetailId) {
        reference.child(String.valueOf(ingredientDetailId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    reference.child(String.valueOf(ingredientDetailId)).removeValue().addOnSuccessListener(aVoid -> {
                        Log.i("IngredientDetail removeIngredientDetailFromFirebase", "IngredientDetail removed successfully");
                    }).addOnFailureListener(e -> {
                        Log.e("IngredientDetail removeIngredientDetailFromFirebase", e.getMessage());
                    });
                }
            }
        });
    }
}
