package com.example.team12.components.menu;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import androidx.appcompat.widget.Toolbar;

import com.example.team12.R;
import com.example.team12.components.FragmentMenu;
import com.example.team12.components.search.ChildModelClass;
import com.example.team12.components.search.FragmentCategory;
import com.example.team12.components.search.IngredientListRedirectInterface;
import com.example.team12.components.search.ParentAdapter;
import com.example.team12.components.search.ParentModelClass;
import com.example.team12.components.search.SearchItemAdapter;
import com.example.team12.components.search.SearchResult;
import com.example.team12.components.search.SearchResultAdapter;
import com.example.team12.entity.IngredientList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentMiniSearch extends Fragment {
    Toolbar toolbar;
    SearchView searchBar;
    RecyclerView recyclerView;
    ArrayList<IngredientList> searchListItem;
    private DatabaseReference databaseReference;
    private DatabaseReference recipeReference;
    private DatabaseReference ingredientReference;
    Fragment parentFragment;
    RecipeAdapter recipeAdapter;
    public FragmentMiniSearch() {}
    public FragmentMiniSearch(Fragment parentFragment, RecipeAdapter sectionAdapter) {
        this.parentFragment = parentFragment;
        this.recipeAdapter = sectionAdapter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.fragment_mini_search, container, false);


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
        toolbar = view.findViewById(R.id.back_toolbar);
        databaseReference = FirebaseDatabase.getInstance("https://calo-a7a97-default-rtdb.firebaseio.com/").getReference();
        ingredientReference = databaseReference.child("Ingredient");
        recipeReference = databaseReference.child("Recipe");
        toolbar.setTitle("Menu");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_main, parentFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .addToBackStack(null)
                        .commit();
            }
        });
        searchBar = view.findViewById(R.id.search_input);
        searchListItem = new ArrayList<>();

//        SearchItemAdapter searchItemAdapter = new SearchItemAdapter(searchListItem, getContext());
        SearchResultAdapter searchAdapter = new SearchResultAdapter(changeAdapter(searchListItem), getContext());
//        searchItemAdapter.notifyDataSetChanged();

        recyclerView = view.findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
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
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
//                    recyclerView.setAdapter(searchAdapter);
                } else {
                    final List<IngredientList> filteredModeList = filter(searchListItem, newText);
                    searchAdapter.setSearchResultList(changeAdapter(filteredModeList));
//                    searchItemAdapter.setFilter((ArrayList<IngredientList>) filteredModeList);
                    recyclerView.setAdapter(searchAdapter);
                }
                return true;
            }
        });
        searchAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(searchAdapter);
    }

    private List<SearchResult> changeAdapter(List<IngredientList> ingredientLists) {
        List<SearchResult> list = new ArrayList<>();
        for (IngredientList model: ingredientLists) {
            if (model != null && model.getName() != null) {
                SearchResult newSearchResult = new SearchResult(model.getName(), model.getDescription(), model.getUrl());
                newSearchResult.recipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main, this);
//                System.out.println("Result: " + newSearchResult.name + ": " + newSearchResult.description);
                newSearchResult.setSearchResultRedirect(new SearchResult.SearchResultRedirect() {
                    @Override
                    public void onClick(View view) {
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frame_layout_main, model.recipeDetailed)
//                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                                .commit();
                        final String[] header = {"Breakfast", "Lunch", "Dinner"};
                        new AlertDialog.Builder(getActivity()).setTitle("Choose meal")
//                                .setNeutralButton("Breakfast", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        header[0] = "Breakfast";
//                                    }
//                                })
//                                .setNeutralButton("Lunch", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        header[0] = "Lunch";
//                                    }
//                                })
//                                .setNeutralButton("Dinner", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        header[0] = "Dinner";
//                                    }
//                                })
                                .setItems(header, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        RecipeModelClass recipeModelClass = new RecipeModelClass(model.getUrl(), header[i], model.getName(), "550kcal");
                                        recipeModelClass.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main, parentFragment);
                                        recipeModelClass.RedirectRecipeModel(new RecipeModelRedirectInterface() {
                                            @Override
                                            public void onClick(View view) {
                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.frame_layout_main, recipeModelClass.fragmentRecipeDetailed)
                                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                                        .commit();
                                            }
                                        });
                                        recipeAdapter.add(recipeModelClass);
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.frame_layout_main, parentFragment)
                                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                                .commit();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create().show();
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
}