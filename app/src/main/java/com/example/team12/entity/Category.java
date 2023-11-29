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
    public static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Categories");
    public static int totalCategories = 0;
    public static int maxCategoryId = 0;
    private int categoryId;
    private String categoryName;
    private String categoryImageURL;
    private String categoryType;

    //Init
    public Category(String categoryName, String categoryImageURL, String categoryType) {
        this.categoryId = maxCategoryId + 1;
        this.categoryName = categoryName;
        this.categoryImageURL = categoryImageURL;
        this.categoryType = categoryType;
    }

    public Category() {
        this.categoryId = maxCategoryId + 1;
        this.categoryName = "Temp Category";
        this.categoryImageURL = "imageURL";
        this.categoryType = "categoryType";
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

    public String getCategoryImageURL() {
        return categoryImageURL;
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

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
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
//                int i = 0;
//                ListVariable.categoryList = Arrays.asList(new Category[totalCategories]);
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Category category = snapshot.getValue(Category.class);
//                    ListVariable.categoryList.set(i, category);
                ListVariable.categoryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = snapshot.getValue(Category.class);
                    ListVariable.categoryList.add(category);
                    if (category.getCategoryId() > maxCategoryId) {
                        maxCategoryId = category.getCategoryId();
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

    public void updateCategoryToFirebase() {
        reference.child(String.valueOf(this.categoryName) + " - " + this.categoryId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("categoryName", this.categoryName);
                    hashMap.put("categoryImageURL", this.categoryImageURL);
                    reference.child(String.valueOf(this.categoryName) + " - " + this.categoryId).updateChildren(hashMap).addOnSuccessListener(
                            aVoid -> Log.i("Category updateCategoryToFirebase", "Category updated successfully")
                    ).addOnFailureListener(
                            e -> Log.e("Category updateCategoryToFirebase", e.getMessage())
                    );
                } else {
                    Log.i("Category updateCategoryToFirebase", "Category does not exist");
                }
            } else {
                Log.e("Category updateCategoryToFirebase", task.getException().getMessage());
            }
        });
    }

    public static List<Category> getCategoryByType(String _categoryType) {
        List<Category> categoryList = ListVariable.categoryList;
        categoryList.clear();
        reference.orderByChild("categoryType").equalTo(_categoryType).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categoryList.add(category);
                }
            } else {
                Log.e("Category getCategoryByType", task.getException().getMessage());
            }
        });
        return categoryList;
    }
}
