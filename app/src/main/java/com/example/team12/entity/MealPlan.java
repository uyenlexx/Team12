package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MealPlan {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MealPlans");
    private static int totalMealPlans = 0;
    private static int maxMealPlanId = 0;
    private int mealPlanId;
    private String mealPlanName;
    private int userId;
    private int mealType;
    private List<Integer> recipeId;
    private String dayOfWeek;

    //Init
    public MealPlan(String mealPlanName, int userId, int mealType, List<Integer> recipeId, String dayOfWeek) {
        this.mealPlanId = maxMealPlanId + 1;
        this.mealPlanName = mealPlanName;
        this.userId = userId;
        this.mealType = mealType;
        this.recipeId = recipeId;
        this.dayOfWeek = dayOfWeek;
    }

    public MealPlan() {
        this.mealPlanId = maxMealPlanId + 1;
        this.mealPlanName = "Temp MealPlan";
        this.userId = 0;
        this.mealType = 0;
        this.recipeId = null;
        this.dayOfWeek = "dayOfWeek";
    }

    // Getters

    public int getMealPlanId() {
        return mealPlanId;
    }

    public String getMealPlanName() {
        return mealPlanName;
    }

    public int getUserId() {
        return userId;
    }

    public int getMealType() {
        return mealType;
    }

    public List<Integer> getRecipeId() {
        return recipeId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public static int getTotalMealPlans() {
        return totalMealPlans;
    }

    public static int getMaxMealPlanId() {
        return maxMealPlanId;
    }

    // Setters

    public void setMealPlanId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public void setMealPlanName(String mealPlanName) {
        this.mealPlanName = mealPlanName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    public void setRecipeId(List<Integer> recipeId) {
        this.recipeId = recipeId;
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

    private CompletableFuture<Void> getMaxMealPlanIdFromFirebase() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        reference.orderByChild("mealPlanId").limitToLast(1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    MealPlan mealPlan = dataSnapshot.getValue(MealPlan.class);
                    if (mealPlan != null) {
                        maxMealPlanId = mealPlan.getMealPlanId();
                    }
                }
                future.complete(null);
            } else {
                Log.e("MealPlan getMaxMealPlanIdFromFirebase", task.getException().getMessage());
            }
        });
        return future;
    }

    private void saveMealPlanToFirebase() {
        this.mealPlanId = maxMealPlanId + 1;
        reference.child(String.valueOf(this.mealPlanId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("MealPlan saveMealPlanToFirebase", "MealPlan does not exist");
                    reference.child(String.valueOf(this.mealPlanId)).setValue(this);
                } else {
                    Log.i("MealPlan saveMealPlanToFirebase", "MealPlan exists");
                }
            } else {
                Log.e("MealPlan saveMealPlanToFirebase", task.getException().getMessage());
            }
        });
    }

    public void saveMealPlan() {
        CompletableFuture<Void> future = getMaxMealPlanIdFromFirebase();
        future.thenRun(this::saveMealPlanToFirebase);
    }

    public void getUserMealPlan(int _userId) {
        ListVariable.mealPlanList.clear();
        reference.orderByChild("userId").equalTo(_userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    MealPlan mealPlan = new MealPlan();
                    mealPlan.setMealPlanId(Integer.parseInt(dataSnapshot.getKey()));
                    mealPlan.setMealPlanName(dataSnapshot.child("mealPlanName").getValue().toString());
                    mealPlan.setUserId(Integer.parseInt(dataSnapshot.child("userId").getValue().toString()));
                    mealPlan.setMealType(Integer.parseInt(dataSnapshot.child("mealType").getValue().toString()));
                    mealPlan.setDayOfWeek(dataSnapshot.child("dayOfWeek").getValue().toString());
                    List<Integer> tempRecipeId = new ArrayList<>();
                    if (dataSnapshot.child("recipeId").getValue() != null) {
                        for (DataSnapshot recipeId : dataSnapshot.child("recipeId").getChildren()) {
                            tempRecipeId.add(Integer.parseInt(recipeId.getKey()));
                        }
                        mealPlan.setRecipeId(tempRecipeId);
                    }
                    mealPlan.setRecipeId(tempRecipeId);
                    ListVariable.mealPlanList.add(mealPlan);

                }
            } else {
                Log.e("MealPlan getUserMealPlan", task.getException().getMessage());
            }
        });
    }

    public void updateMealPlanToFirebase() {
        reference.child(String.valueOf(this.mealPlanId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    HashMap<String, Object> updateMap = new HashMap<>();
                    updateMap.put("mealPlanName", this.mealPlanName);
                    updateMap.put("userId", this.userId);
                    updateMap.put("mealType", this.mealType);
                    updateMap.put("recipeId", this.recipeId);
                    updateMap.put("dayOfWeek", this.dayOfWeek);
                    reference.child(String.valueOf(this.mealPlanId)).updateChildren(updateMap).addOnSuccessListener(aVoid -> {
                        Log.i("MealPlan updateMealPlanToFirebase", "MealPlan updated");
                    }).addOnFailureListener(e -> {
                        Log.e("MealPlan updateMealPlanToFirebase", e.getMessage());
                    });
                }
            }
        });
    }

    public static void deleteMealPlanFromFirebase(int _mealPlanId) {
        reference.child(String.valueOf(_mealPlanId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    reference.child(String.valueOf(_mealPlanId)).removeValue().addOnSuccessListener(aVoid -> {
                        Log.i("MealPlan deleteMealPlanFromFirebase", "MealPlan deleted");
                    }).addOnFailureListener(e -> {
                        Log.e("MealPlan deleteMealPlanFromFirebase", e.getMessage());
                    });
                }
            }
        });
    }
}
