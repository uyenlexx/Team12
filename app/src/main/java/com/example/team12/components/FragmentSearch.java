package com.example.team12.components;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.team12.R;
import com.example.team12.components.calculator.FragmentMealCalculator;
import com.example.team12.components.home.MyAdapter;
import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.menu.RecipeAdapter;
import com.example.team12.components.menu.RecipeModelClass;
import com.example.team12.components.search.ChildModelClass;
import com.example.team12.components.search.FragmentCategory;
import com.example.team12.components.search.FragmentSearchNotFound;
import com.example.team12.components.search.IngredientListRedirectInterface;
import com.example.team12.components.search.ParentAdapter;
import com.example.team12.components.search.ParentModelClass;
import com.example.team12.components.search.SearchItemAdapter;
import com.example.team12.components.search.SearchResult;
import com.example.team12.components.search.SearchResultAdapter;
import com.example.team12.entity.Category;
import com.example.team12.entity.Ingredient;
import com.example.team12.entity.IngredientList;
import com.example.team12.entity.ListVariable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class FragmentSearch extends Fragment {
//    Integer[] drawableList = new Integer[R.drawable.img_fruits, R.drawable.img_vegetables, R.drawable.img_meat, R.drawable.img_seafood, R.drawable.img_dessert, R.drawable.img_drinks, R.drawable.img_fried, R.drawable.img_grilled, R.drawable.img_steamed];
    SearchView searchBar;
    RecyclerView recyclerView;
    ArrayList<ParentModelClass> parentModelClasses;
    ArrayList<ChildModelClass> ingredientsArrayList;
    ArrayList<ChildModelClass> recipeArrayList;
    ArrayList<IngredientList> searchListItem;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private DatabaseReference databaseReference;
    private DatabaseReference recipeReference;


    private DatabaseReference ingredientReference;

    FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);

        return searchView;
    }

    private void addIngredientList(IngredientList ingredientList) {
        ingredientList.recipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main, this);
        ingredientList.setIngredientListRedirectInterface(new IngredientListRedirectInterface() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_main, ingredientList.recipeDetailed)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        searchListItem.add(ingredientList);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = view.findViewById(R.id.frame_layout_search);
        searchBar = view.findViewById(R.id.search_input);
        searchListItem = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://calo-a7a97-default-rtdb.firebaseio.com/").getReference();
        ingredientReference = databaseReference.child("Ingredient");
        recipeReference = databaseReference.child("Recipe");

        SearchItemAdapter searchItemAdapter = new SearchItemAdapter(searchListItem, getContext());
        SearchResultAdapter searchAdapter = new SearchResultAdapter(new ArrayList<>(), getContext());
        searchItemAdapter.notifyDataSetChanged();

        recyclerView = view.findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ingredientsArrayList = new ArrayList<>();
        recipeArrayList = new ArrayList<>();
        parentModelClasses = new ArrayList<>();
        addCategory();
//        ChildModelClass fruits = new ChildModelClass(R.drawable.img_fruits, getText(R.string.fruits).toString(), R.color.fade_red);
//        fruits.setChildModelClassRedirect(new ChildModelClass.ChildModelClassRedirect() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout_search, new FragmentCategory())
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit();
//            }
//        });
//        ingredientsArrayList.add(fruits);
//        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_vegetables, getText(R.string.vegetables).toString(), R.color.fade_green));
//        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_meat, getText(R.string.meat).toString(), R.color.fade_yellow));
//        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_seafood, getText(R.string.seafood).toString(), R.color.fade_blue));
//
        parentModelClasses.add(new ParentModelClass(getText(R.string.ingredients_category).toString(), ingredientsArrayList));
//
//        recipeArrayList.add(new ChildModelClass(R.drawable.img_dessert, getText(R.string.dessert).toString(), R.color.fade_red));
//        recipeArrayList.add(new ChildModelClass(R.drawable.img_drinks, getText(R.string.drink).toString(), R.color.fade_green));
//        recipeArrayList.add(new ChildModelClass(R.drawable.img_fried, getText(R.string.fried).toString(), R.color.fade_yellow));
//        recipeArrayList.add(new ChildModelClass(R.drawable.img_grilled, getText(R.string.grilled).toString(), R.color.fade_blue));
//        recipeArrayList.add(new ChildModelClass(R.drawable.img_steamed, getText(R.string.steamed).toString(), R.color.fade_purple));

        parentModelClasses.add(new ParentModelClass(getText(R.string.recipes_category).toString(), recipeArrayList));
//        parentModelClasses.add(new ParentModelClass(getText(R.string.recipes_category).toString(), searchListItem));

        ParentAdapter parentAdapter = new ParentAdapter(parentModelClasses, getContext());
        parentAdapter.notifyDataSetChanged();

        ingredientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                searchListItem.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        IngredientList ingredientList = postSnapshot.getValue(IngredientList.class);
                        addIngredientList(ingredientList);
                        Log.d("searchInredientItem", searchListItem.toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("SearchError", databaseError.getMessage());
            }
        });
        recipeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                searchListItem.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        IngredientList ingredientList = postSnapshot.getValue(IngredientList.class);
//                        searchListItem.add(ingredientList);
                        addIngredientList(ingredientList);
                        Log.d("searchRecipeItem", searchListItem.toString());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("SearchError", databaseError.getMessage());
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    final List<IngredientList> filteredModeList = filter(searchListItem, query);
                    final List<SearchResult> searchResultList = changeAdapter(filteredModeList);
                    searchAdapter.setSearchResultList(searchResultList);
                    searchItemAdapter.setFilter((ArrayList<IngredientList>) filteredModeList);
                    recyclerView.setAdapter(searchAdapter);
                }
//                System.out.println("query submitted: " + query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    recyclerView.setAdapter(parentAdapter);
                } else {
                    final List<IngredientList> filteredModeList = filter(searchListItem, newText);
                    searchItemAdapter.setFilter((ArrayList<IngredientList>) filteredModeList);
                    recyclerView.setAdapter(searchItemAdapter);
                }
                return true;
            }
        });
        recyclerView.setAdapter(parentAdapter);
    }

    private List<SearchResult> changeAdapter(List<IngredientList> ingredientLists) {
        List<SearchResult> list = new ArrayList<>();
        for (IngredientList model: ingredientLists) {
            if (model != null && model.getName() != null) {
                SearchResult newSearchResult = new SearchResult(model.getName(), model.getDescription(), model.getUrl());
                newSearchResult.recipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_search, this);
//                System.out.println("Result: " + newSearchResult.name + ": " + newSearchResult.description);
                newSearchResult.setSearchResultRedirect(new SearchResult.SearchResultRedirect() {
                    @Override
                    public void onClick(View view) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout_search, model.recipeDetailed)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    }
                });
                list.add(newSearchResult);
            }
        }
//        System.out.println("Test: " + list.size());
        return list;
    }

    private List<IngredientList> filter(List<IngredientList> p1, String query) {
        query = query.toLowerCase();
        final List<IngredientList> filteredModeList = new ArrayList<>();
        for (IngredientList model : p1) {
            if (model != null && model.getName() != null) {
                final String text = model.getName().toLowerCase();
                if (text.contains(query)) {
                    filteredModeList.add(model);
                }
            }
        }
        return filteredModeList;
    }

    public void addCategory() {
        for (Category category : ListVariable.categoryList) {
            int icon, color;
            if (category.getCategoryName().equals("Fruits")) {
                icon = R.drawable.img_fruits;
                color = R.color.fade_red;
            } else if (category.getCategoryName().equals("Vegetables")) {
                icon = R.drawable.img_vegetables;
                color = R.color.fade_green;
            } else if (category.getCategoryName().equals("Meat")) {
                icon = R.drawable.img_meat;
                color = R.color.fade_yellow;
            } else if (category.getCategoryName().equals("Seafood")) {
                icon = R.drawable.img_seafood;
                color = R.color.fade_blue;
            } else if (category.getCategoryName().equals("Dessert")) {
                icon = R.drawable.img_dessert;
                color = R.color.fade_red;
            } else if (category.getCategoryName().equals("Drink")) {
                icon = R.drawable.img_drinks;
                color = R.color.fade_green;
            } else if (category.getCategoryName().equals("Fried")) {
                icon = R.drawable.img_fried;
                color = R.color.fade_yellow;
            } else if (category.getCategoryName().equals("Grilled")) {
                icon = R.drawable.img_grilled;
                color = R.color.fade_blue;
            } else if (category.getCategoryName().equals("Steamed")) {
                icon = R.drawable.img_steamed;
                color = R.color.fade_purple;
            } else {
                icon = R.drawable.img_steamed;
                color = R.color.fade_purple;
            }

            Log.i("Category", category.getCategoryType() + " " + color + " " + icon);
            ChildModelClass newCategory = new ChildModelClass(icon, category, color);
            Log.i("Category", category.getCategoryName());
            newCategory.setChildModelClassRedirect(new ChildModelClass.ChildModelClassRedirect() {
                @Override
                public void onClick(View view) {
                    ListVariable.currentCategory = category;
                    FragmentCategory fragmentCategory = new FragmentCategory();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout_main, fragmentCategory)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            });
            if (category.getCategoryType().equals("Ingredient")) {
                ingredientsArrayList.add(newCategory);
            } else {
                recipeArrayList.add(newCategory);
            }
        }
    }
}