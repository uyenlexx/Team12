package com.example.team12.entity;

import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetail {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("RecipeDetails");
    private int recipeDetailId;
    private int protein;
    private int calories;
    private int fat;
    private int carbs;
    private String steps;
    private List<Pair<Integer, Integer>> ingredientList;

    public RecipeDetail(int recipeDetailId, int protein, int calories, int fat, int carbs, String steps, List<Pair<Integer, Integer>> ingredientList) {
        this.recipeDetailId = recipeDetailId;
        this.protein = protein;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.steps = steps;
        this.ingredientList = ingredientList;
    }

    public RecipeDetail() {
        this.recipeDetailId = 0;
        this.protein = 0;
        this.calories = 0;
        this.fat = 0;
        this.carbs = 0;
        this.steps = "";
        this.ingredientList = null;
    }

    // Getters

    public int getRecipeDetailId() {
        return recipeDetailId;
    }

    public int getProtein() {
        return protein;
    }

    public int getCalories() {
        return calories;
    }

    public int getFat() {
        return fat;
    }

    public int getCarbs() {
        return carbs;
    }

    public String getSteps() {
        return steps;
    }

    public List<Pair<Integer, Integer>> getIngredientList() {
        return ingredientList;
    }

    // Setters

    public void setRecipeDetailId(int recipeDetailId) {
        this.recipeDetailId = recipeDetailId;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public void setIngredientList(List<Pair<Integer, Integer>> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public static void getRecipeDetailById(int recipeId) {
        reference.orderByChild("recipeDetailId").equalTo(recipeId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    RecipeDetail recipeDetail = new RecipeDetail();
                    recipeDetail.setRecipeDetailId(dataSnapshot.child("recipeDetailId").getValue(Integer.class));
                    recipeDetail.setProtein(dataSnapshot.child("protein").getValue(Integer.class));
                    recipeDetail.setCalories(dataSnapshot.child("calories").getValue(Integer.class));
                    recipeDetail.setFat(dataSnapshot.child("fat").getValue(Integer.class));
                    recipeDetail.setCarbs(dataSnapshot.child("carbs").getValue(Integer.class));
                    recipeDetail.setSteps(dataSnapshot.child("steps").getValue(String.class));
                    List<Pair<Integer, Integer>> tempIngredientList = new ArrayList<>();
                    for (DataSnapshot ingredientSnapshot : dataSnapshot.child("Ingredient").getChildren()) {
                        Pair<Integer, Integer> ingredient = new Pair<>(Integer.parseInt(ingredientSnapshot.getKey()), ingredientSnapshot.getValue(Integer.class));
                        tempIngredientList.add(ingredient);
                    }
                    recipeDetail.setIngredientList(tempIngredientList);
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
                }
            } else {
                System.out.println("RecipeDetail: " + task.getException());
            }
        });
    }

    public void saveRecipeDetailToFirebase() {
        reference.child(String.valueOf(this.recipeDetailId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    reference.child(String.valueOf(this.recipeDetailId)).setValue(this);
                }
            }
        });
    }
}
