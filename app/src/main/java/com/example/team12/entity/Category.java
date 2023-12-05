package com.example.team12.entity;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Category {
    public static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Category");
    public static int totalCategories = 0;
    public static int maxCategoryId = 0;
    private int categoryId;
    private String categoryName;
    private String categoryType;

    //Init
    public Category(String categoryName, int categoryIcon, String categoryType) {
        this.categoryId = maxCategoryId + 1;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public Category() {
        this.categoryId = maxCategoryId + 1;
        this.categoryName = "Temp Category";
        this.categoryType = "Temp Category Type";
    }

    // Getters

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static int getTotalCategories() {
        return totalCategories;
    }

    public static int getMaxCategoryId() {
        return maxCategoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    // Setters

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static void setTotalCategories(int totalCategories) {
        Category.totalCategories = totalCategories;
    }

    public static void setMaxCategoryId(int maxCategoryId) {
        Category.maxCategoryId = maxCategoryId;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public static void setUpFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalCategories = (int) dataSnapshot.getChildrenCount();
                maxCategoryId = 0;
                ListVariable.categoryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String categoryType = snapshot.getKey();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Category category = new Category();
                        category.setCategoryId(Integer.parseInt(snapshot1.getKey()));
                        category.setCategoryName(snapshot1.child("name").getValue().toString());
                        category.setCategoryType(categoryType);
                        ListVariable.categoryList.add(category);
                        if (category.getCategoryId() > maxCategoryId) {
                            maxCategoryId = category.getCategoryId();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Category setUpFirebase", "onCancelled", databaseError.toException());
            }
        });
    }

    public void saveCategoryToFirebase() {
    HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("name", this.categoryName);
        hashMap.put(String.valueOf(this.categoryId), hashMap1);
        reference.child(this.categoryType).updateChildren(hashMap).addOnSuccessListener(
                aVoid -> Log.i("Category saveCategoryToFirebase", "Category saved successfully")
        ).addOnFailureListener(
                e -> Log.e("Category saveCategoryToFirebase", e.getMessage())
        );
    }

    public void updateCategoryToFirebase() {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("name", this.categoryName);
        hashMap.put(String.valueOf(this.categoryId), hashMap1);
        reference.child(this.categoryType).updateChildren(hashMap).addOnSuccessListener(
                aVoid -> Log.i("Category updateCategoryToFirebase", "Category updated successfully")
        ).addOnFailureListener(
                e -> Log.e("Category updateCategoryToFirebase", e.getMessage())
        );
    }

    public static List<Category> getCategoryByType(String _categoryType) {
        List<Category> categoryList = null;
        for (Category category : ListVariable.categoryList) {
            if (category.getCategoryType().equals(_categoryType)) {
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
