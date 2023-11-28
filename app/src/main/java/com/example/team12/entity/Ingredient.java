package com.example.team12.entity;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ingredient {
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Ingredients");
    private static int totalIngredients = 0;
    private static int maxIngredientId = 0;
    private int ingredientId;
    private String ingredientName;
    private int categoryId;
    private String imageURL;
    private String description;
    private int userId;

    //Init
    public Ingredient(String ingredientName, int categoryId, String imageURL, String description, int userId) {
        this.ingredientId = maxIngredientId + 1;
        this.ingredientName = ingredientName;
        this.categoryId = categoryId;
        this.imageURL = imageURL;
        this.description = description;
        this.userId = userId;
    }

    public Ingredient() {
        this.ingredientId = maxIngredientId + 1;
        this.ingredientName = "Temp Ingredient";
        this.categoryId = 0;
        this.imageURL = "imageURL";
        this.description = "description";
        this.userId = 0;
    }

    // Getters
    public int getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
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


    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
                ListVariable.ingredientList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ingredient ingredient = snapshot.getValue(Ingredient.class);
                    if (ingredient.getIngredientId() > maxIngredientId) {
                        maxIngredientId = ingredient.getIngredientId();
                    }
                    ListVariable.ingredientList.add(ingredient);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Ingredient setUpFirebase", "onCancelled", databaseError.toException());
            }
        });
    }

//    public static void getIngredientByCategory(int _categoryId) {
//        reference.orderByChild("categoryId").equalTo(_categoryId).get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
//                    Ingredient ingredient = dataSnapshot.getValue(Ingredient.class);
//                    if (ingredient != null) {
//                        //Check if Value already exists
//                        boolean exists = false;
//                        for (Ingredient ingredient1 : ListVariable.ingredientList) {
//                            if (ingredient1.getIngredientId() == ingredient.getIngredientId()) {
//                                if (ingredient1 == ingredient) {
//                                    exists = true;
//                                    break;
//                                }
//                                else {
//                                    ListVariable.ingredientList.remove(ingredient1);
//                                    ListVariable.ingredientList.add(ingredient);
//                                    exists = true;
//                                    break;
//                                }
//                            }
//                        }
//                        if (!exists) {
//                            ListVariable.ingredientList.add(ingredient);
//                        }
//                    }
//                }
//            }
//            else {
//                Log.e("Ingredient getIngredientByCategory", task.getException().getMessage());
//            }
//        });
//    }

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
