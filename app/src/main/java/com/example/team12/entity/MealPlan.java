package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MealPlan {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MealPlans");
    private static int totalMealPlans = 0;
    private static int maxMealPlanId = 0;
    private int mealPlanId;
    private int userId;
    private int mealType;
    private int[] recipeId;
    private String dayOfWeek;

    public MealPlan(int mealPlanId, int userId, int mealType, int[] recipeId, String dayOfWeek) {
        this.mealPlanId = mealPlanId;
        this.userId = userId;
        this.mealType = mealType;
        this.recipeId = recipeId;
        this.dayOfWeek = dayOfWeek;
    }

    public int getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMealType() {
        return mealType;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    public int[] getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int[] recipeId) {
        this.recipeId = recipeId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

//    public static void setUpFirebase() {
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                totalMealPlans = (int) snapshot.getChildrenCount();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    MealPlan mealPlan = dataSnapshot.getValue(MealPlan.class);
//                    if (mealPlan != null) {
//                        if (mealPlan.getMealPlanId() > maxMealPlanId) {
//                            maxMealPlanId = mealPlan.getMealPlanId();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("MealPlan setUpFirebase", error.getMessage());
//            }
//        });
//    }

    public void getTotalMealPlan() {
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                totalMealPlans = (int) task.getResult().getChildrenCount();
            } else {
                Log.e("MealPlan getTotalMealPlan", task.getException().getMessage());
            }
        });
    }

    public void saveMealPlanToFirebase() {
        reference.child(String.valueOf(this.mealPlanId) + " - " + this.userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("MealPlan saveMealPlanToFirebase", "MealPlan does not exist");
                    reference.child(String.valueOf(this.mealPlanId) + " - " + this.userId).setValue(this);
                } else {
                    Log.i("MealPlan saveMealPlanToFirebase", "MealPlan exists");
                }
            } else {
                Log.e("MealPlan saveMealPlanToFirebase", task.getException().getMessage());
            }
        });
    }
}
