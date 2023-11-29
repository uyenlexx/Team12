package com.example.team12.entity;

import android.util.Log;

import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {
    public static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
    public static int maxUserId = 0;
    public static int totalUsers = 0;
    private int userId;
    private String username;
    private String dateOfBirth;
    private String email;
    private String name;
    private String uid;

    private List<Integer> favoriteRecipes;
    private List<Integer> favoriteIngredients;

    //Init
    public User(String username, String dateOfBirth, String email, String name, String uid, List<Integer> favoriteRecipes, List<Integer> favoriteIngredients) {
        this.userId = maxUserId + 1;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.favoriteRecipes = favoriteRecipes;
        this.favoriteIngredients = favoriteIngredients;
    }

    public User(String username, String dateOfBirth, String email, String name, String uid) {
        this.userId = maxUserId + 1;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.favoriteRecipes = null;
        this.favoriteIngredients = null;
    }

    public User() {
        this.userId = maxUserId + 1;
        this.username = "Temp User";
        this.dateOfBirth = "dateOfBirth";
        this.email = "email";
        this.name = "name";
        this.uid = "uid";
        this.favoriteRecipes = null;
        this.favoriteIngredients = null;
    }

    // Getters

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public List<Integer> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public List<Integer> getFavoriteIngredients() {
        return favoriteIngredients;
    }

    public static int getMaxUserId() {
        return maxUserId;
    }

    public static int getTotalUsers() {
        return totalUsers;
    }

    // Setters

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFavoriteRecipes(List<Integer> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public void setFavoriteIngredients(List<Integer> favoriteIngredients) {
        this.favoriteIngredients = favoriteIngredients;
    }

    public static void setMaxUserId(int maxUserId) {
        User.maxUserId = maxUserId;
    }

    public static void setTotalUsers(int totalUsers) {
        User.totalUsers = totalUsers;
    }

    private CompletableFuture<Void> getMaxUserIdFromFirebase() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        reference.orderByChild("userId").limitToLast(1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot child : task.getResult().getChildren()) {
                    maxUserId = Integer.parseInt(child.child("userId").getValue().toString());
                }
                future.complete(null);
            } else {
                System.out.println("User getMaxUserIdFromFirebase: " + task.getException().getMessage());
            }
        });
        return future;
    }

    public void addUserToFirebase() {
        CompletableFuture<Void> future = getMaxUserIdFromFirebase();
        future.thenAcceptAsync(aVoid -> {
            HashMap<String, String> userMap = new HashMap<>();
            userMap.put("userId", String.valueOf(maxUserId + 1));
            userMap.put("username", this.username);
            userMap.put("dateOfBirth", this.dateOfBirth);
            userMap.put("email", this.email);
            userMap.put("name", this.name);
            userMap.put("uid", this.uid);
            reference.child(username).setValue(userMap);
            if (this.favoriteRecipes != null) {
                for (Integer recipeId : this.favoriteRecipes) {
                    reference.child(username).child("favoriteRecipes").child(String.valueOf(recipeId)).setValue(true);
                }
                for (Integer ingredientId : this.favoriteIngredients) {
                    reference.child(username).child("favoriteIngredients").child(String.valueOf(ingredientId)).setValue(true);
                }
            }
        });
    }

    public void addFavoriteRecipeToFirebase(int recipeId) {
        //Check if recipeId already exists on Firebase
        AtomicBoolean exists = new AtomicBoolean(false);
        reference.child(username).child("favoriteRecipes").child(String.valueOf(recipeId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    exists.set(true);
                }
            } else {
                System.out.println("User addFavoriteRecipeToFirebase: " + task.getException().getMessage());
            }
        });
        if (!exists.get()) {
            reference.child(username).child("favoriteRecipes").child(String.valueOf(recipeId)).setValue(true);
        }
    }

    public void addFavoriteIngredientToFirebase(int ingredientId) {
        //Check if ingredientId already exists on Firebase
        AtomicBoolean exists = new AtomicBoolean(false);
        reference.child(username).child("favoriteIngredients").child(String.valueOf(ingredientId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    exists.set(true);
                }
            } else {
                System.out.println("User addFavoriteIngredientToFirebase: " + task.getException().getMessage());
            }
        });
        if (!exists.get()) {
            reference.child(username).child("favoriteIngredients").child(String.valueOf(ingredientId)).setValue(true);
        }
    }

    public void removeFavoriteRecipeFromFirebase(int recipeId) {
        //Check if recipeId already exists on Firebase
        AtomicBoolean exists = new AtomicBoolean(false);
        reference.child(username).child("favoriteRecipes").child(String.valueOf(recipeId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    exists.set(true);
                }
            } else {
                System.out.println("User removeFavoriteRecipeFromFirebase: " + task.getException().getMessage());
            }
        });
        if (exists.get()) {
            reference.child(username).child("favoriteRecipes").child(String.valueOf(recipeId)).removeValue();
        }
    }

    public void removeFavoriteIngredientFromFirebase(int ingredientId) {
        //Check if ingredientId already exists on Firebase
        AtomicBoolean exists = new AtomicBoolean(false);
        reference.child(username).child("favoriteIngredients").child(String.valueOf(ingredientId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getValue() != null) {
                    exists.set(true);
                }
            } else {
                System.out.println("User removeFavoriteIngredientFromFirebase: " + task.getException().getMessage());
            }
        });
        if (exists.get()) {
            reference.child(username).child("favoriteIngredients").child(String.valueOf(ingredientId)).removeValue();
        }
    }

    public void updateUserInfoToFirebase() {
        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("dateOfBirth", this.dateOfBirth);
        updateMap.put("email", this.email);
        updateMap.put("name", this.name);
        reference.child(username).updateChildren(updateMap);
    }

    public void updatePasswordToFirebase(String oldPassword, String newPassword) {
        final FirebaseUser user = ListVariable.firebaseAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(this.email, oldPassword);
        user.reauthenticate(credential).addOnSuccessListener(aVoid -> {
            user.updatePassword(newPassword).addOnSuccessListener(aVoid1 -> {
                Log.i("User updatePasswordToFirebase", "Password updated");
            }).addOnFailureListener(e -> {
                Log.e("User updatePasswordToFirebase", e.getMessage());
            });
        }).addOnFailureListener(e -> {
            Log.e("User updatePasswordToFirebase", e.getMessage());
        });
    }
}