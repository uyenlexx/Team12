package com.example.team12.entity;

import android.util.Log;
import android.util.Pair;

import com.example.team12.components.listener.RecipeDetailCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeDetail {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("RecipeDetail");
    private int recipeDetailId;
    private float protein;
    private float calories;
    private float fat;
    private float carbs;
    private String step;
    private HashMap<String, Pair<Integer, Pair<Integer, String>>> ingredient;

    //Init
    public RecipeDetail(int recipeDetailId, float protein, float calories, float fat, float carbs, String step, HashMap<String, Pair<Integer, Pair<Integer, String>>> ingredient) {
        this.recipeDetailId = recipeDetailId;
        this.protein = protein;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.step = step;
        this.ingredient = ingredient;
    }

    public RecipeDetail() {
        this.recipeDetailId = 0;
        this.protein = 0;
        this.calories = 0;
        this.fat = 0;
        this.carbs = 0;
        this.step = "";
        this.ingredient = new HashMap<>();
    }

    // Getters

    public int getRecipeDetailId() {
        return recipeDetailId;
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

    public String getStep() {
        return step;
    }

    public HashMap<String, Pair<Integer, Pair<Integer, String>>> getIngredient() {
        return ingredient;
    }

    // Setters

    public void setRecipeDetailId(int recipeDetailId) {
        this.recipeDetailId = recipeDetailId;
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


    public void setStep(String steps) {
        this.step = steps;
    }

    public void setIngredient(HashMap<String, Pair<Integer, Pair<Integer, String>>> ingredient) {
        this.ingredient = ingredient;
    }

    public static void getRecipeDetailById(int recipeId, RecipeDetailCallback myCallback) {
        Log.i("RecipeDetail getRecipeDetailById", "Loading RecipeDetail");

        reference.orderByKey().equalTo(String.valueOf(recipeId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i("RecipeDetail getRecipeDetailById", "RecipeDetail loaded successfully 1");
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    RecipeDetail recipeDetail = new RecipeDetail();
                    recipeDetail.setRecipeDetailId(dataSnapshot.child("recipeDetailId").getValue(Integer.class));
                    recipeDetail.setProtein(dataSnapshot.child("protein").getValue(Float.class));
                    recipeDetail.setCalories(dataSnapshot.child("calories").getValue(Float.class));
                    recipeDetail.setFat(dataSnapshot.child("fat").getValue(Float.class));
                    recipeDetail.setCarbs(dataSnapshot.child("carbs").getValue(Float.class));
                    recipeDetail.setStep(dataSnapshot.child("step").getValue(String.class));
                    HashMap<String, Pair<Integer, Pair<Integer, String>>> ingredient = new HashMap<>();
                    Log.i("RecipeDetail getRecipeDetailById", "RecipeDetail loaded successfully 1.5");
                    for (DataSnapshot ingredientSnapshot : dataSnapshot.child("ingredient").getChildren()) {
                        //Get all ingredient name, all of it is in children element
                        int ingredientId = ingredientSnapshot.child("ingredientId").getValue(Integer.class);
                        int quantity = ingredientSnapshot.child("Quantity").getValue(Integer.class);
                        String unit = ingredientSnapshot.child("unit").getValue(String.class);
                        Pair<Integer, Pair<Integer, String>> ingredientDetail = new Pair<>(ingredientId, new Pair<>(quantity, unit));
                        ingredient.put(ingredientSnapshot.getKey(), ingredientDetail);
                    }
                    recipeDetail.setIngredient(ingredient);
                    //Check if Value already exists
                    boolean exists = false;
                    for (RecipeDetail recipeDetail1 : ListVariable.recipeDetailList) {
                        if (recipeDetail1.getRecipeDetailId() == recipeDetail.getRecipeDetailId()) {
                            if (recipeDetail1 == recipeDetail) {
                                exists = true;
                                break;
                            } else {
                                ListVariable.recipeDetailList.remove(recipeDetail1);
                                ListVariable.recipeDetailList.add(recipeDetail);
                                exists = true;
                                break;
                            }
                        }
                    }
                    if (!exists) {
                        ListVariable.recipeDetailList.add(recipeDetail);
                    }
                    Log.i("RecipeDetail getRecipeDetailById", "RecipeDetail loaded successfully 2");
                    myCallback.onCallback(recipeDetail);
                }
                Log.i("RecipeDetail getRecipeDetailById", "RecipeDetail loaded successfully");
            } else {
                System.out.println("RecipeDetail: " + task.getException());
            }
        });
    }

    public void saveRecipeDetailToFirebase() {
        reference.child(String.valueOf(this.recipeDetailId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("recipeDetailId", this.recipeDetailId);
                    hashMap.put("protein", this.protein);
                    hashMap.put("calories", this.calories);
                    hashMap.put("fat", this.fat);
                    hashMap.put("carbs", this.carbs);
                    hashMap.put("step", this.step);
                    HashMap<String, Object> ingredientHashMap = new HashMap<>();
                    for (String ingredientName : this.ingredient.keySet()) {
                        HashMap<String, Object> ingredientDetailHashMap = new HashMap<>();
                        ingredientDetailHashMap.put("ingredientId", this.ingredient.get(ingredientName).first);
                        ingredientDetailHashMap.put("Quantity", this.ingredient.get(ingredientName).second.first);
                        ingredientDetailHashMap.put("unit", this.ingredient.get(ingredientName).second.second);
                        ingredientHashMap.put(ingredientName, ingredientDetailHashMap);
                    }
                    hashMap.put("ingredient", ingredientHashMap);
                    reference.child(String.valueOf(this.recipeDetailId)).setValue(hashMap);
                }
            }
        });
    }

    public void updateRecipeDetailToFirebase() {
        reference.child(String.valueOf(this.recipeDetailId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("protein", this.protein);
                    hashMap.put("calories", this.calories);
                    hashMap.put("fat", this.fat);
                    hashMap.put("carbs", this.carbs);
                    hashMap.put("step", this.step);
                    reference.child(String.valueOf(this.recipeDetailId)).updateChildren(hashMap);
                }
            }
        });
    }

    public static void removeRecipeDetailFromFirebase(int recipeDetailId) {
        reference.child(String.valueOf(recipeDetailId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    reference.child(String.valueOf(recipeDetailId)).removeValue().addOnSuccessListener(aVoid -> {
                        Log.i("RecipeDetail removeRecipeDetailFromFirebase", "RecipeDetail removed successfully");
                    }).addOnFailureListener(e -> {
                        Log.e("RecipeDetail removeRecipeDetailFromFirebase", e.getMessage());
                    });
                }
            }
        });
    }

//    public void calculateNutrition() {
//        if (this.ingredient != null) {
//            this.protein = 0;
//            this.calories = 0;
//            this.fat = 0;
//            this.carbs = 0;
//            for (Pair<Integer, Integer> ingredient : this.ingredient) {
//                IngredientDetail tempIngredientDetail = new IngredientDetail();
//                tempIngredientDetail.getIngredientDetail(ingredient.first);
//                this.protein += tempIngredientDetail.getProtein() * ingredient.second;
//                this.calories += tempIngredientDetail.getCalories() * ingredient.second;
//                this.fat += tempIngredientDetail.getFat() * ingredient.second;
//                this.carbs += tempIngredientDetail.getCarbs() * ingredient.second;
//            }
//            updateRecipeDetailToFirebase();
//        } else {
//            Log.e("RecipeDetail calculateNutrition", "Ingredient list is null");
//        }
//    }
}
