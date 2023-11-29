package com.example.team12.entity;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Category {
    public static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Categories");
    public static int totalCategories = 0;
    public static int maxCategoryId = 0;
    private int categoryId;
    private String categoryName;

    public Category(int categoryId, String categoryName) {
        this.categoryId = maxCategoryId + 1;
        this.categoryName = categoryName;
    }

    public Category() {
        this.categoryId = maxCategoryId + 1;
        this.categoryName = "Temp Category";
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

    public static void setUpFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalCategories = (int) dataSnapshot.getChildrenCount();
                maxCategoryId = 0;
                int i = 0;
                ListVariable.categoryList = Arrays.asList(new Category[totalCategories]);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = snapshot.getValue(Category.class);
                    ListVariable.categoryList.set(i, category);
                    if (category.getCategoryId() > maxCategoryId) {
                        maxCategoryId = category.getCategoryId();
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Category setUpFirebase", "onCancelled", databaseError.toException());
            }
        });
    }

    public void saveCategoryToFirebase() {
        reference.child(String.valueOf(this.categoryName) + " - " + this.categoryId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() == null) {
                    Log.i("Category saveCategoryToFirebase", "Category does not exist");
                    reference.child(String.valueOf(this.categoryName) + " - " + this.categoryId).setValue(this);
                } else {
                    Log.i("Category saveCategoryToFirebase", "Category already exists");
                }
            } else {
                Log.e("Category saveCategoryToFirebase", task.getException().getMessage());
            }
        });
    }
}
