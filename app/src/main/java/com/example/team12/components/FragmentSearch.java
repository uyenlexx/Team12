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
import com.example.team12.components.search.ChildModelClass;
import com.example.team12.components.search.FragmentSearchNotFound;
import com.example.team12.components.search.ParentAdapter;
import com.example.team12.components.search.ParentModelClass;
import com.example.team12.components.search.SearchItemAdapter;
import com.example.team12.entity.Ingredient;
import com.example.team12.entity.IngredientList;
import com.example.team12.entity.Recipe;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class FragmentSearch extends Fragment {
    SearchView searchBar;
    RecyclerView recyclerView;
    RecyclerView searchList;
    ArrayList<ParentModelClass> parentModelClasses;
    ArrayList<ChildModelClass> ingredientsArrayList;
    ArrayList<ChildModelClass> recipeArrayList;
    ArrayList<IngredientList> searchListItem;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private DatabaseReference databaseReference;
//    FirebaseRecyclerOptions<Recipe, Ingredient>

    FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);

        return searchView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = view.findViewById(R.id.frame_layout_search);
        searchBar = view.findViewById(R.id.search_input);
        searchListItem = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://calo-a7a97-default-rtdb.firebaseio.com/").getReference().child("Ingredient");
        searchList = view.findViewById(R.id.search_recycler_view2);
        searchList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        SearchItemAdapter searchItemAdapter = new SearchItemAdapter(searchListItem, getContext());
        searchItemAdapter.notifyDataSetChanged();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                searchListItem.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        IngredientList ingredientList = postSnapshot.getValue(IngredientList.class);
                        searchListItem.add(ingredientList);
                        Log.d("searchListItemA", searchListItem.toString());

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("SearchError", databaseError.getMessage());
            }
        });
        searchList.setAdapter(searchItemAdapter);
//        searchList.setVisibility(view.INVISIBLE);


        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                final List<IngredientList> filteredModeList = filter(searchListItem, newText);
                searchItemAdapter.setFilter((ArrayList<IngredientList>) filteredModeList);
                return true;
            }

        });

        recyclerView = view.findViewById(R.id.search_recycler_view);

        ingredientsArrayList = new ArrayList<>();
        recipeArrayList = new ArrayList<>();
        parentModelClasses = new ArrayList<>();

        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_fruits, searchListItem.toString(), R.color.fade_red));
        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_vegetables, getText(R.string.vegetables).toString(), R.color.fade_green));
        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_meat, getText(R.string.meat).toString(), R.color.fade_yellow));
        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_seafood, getText(R.string.seafood).toString(), R.color.fade_blue));

        parentModelClasses.add(new ParentModelClass(getText(R.string.ingredients_category).toString(), ingredientsArrayList));

        recipeArrayList.add(new ChildModelClass(R.drawable.img_dessert, getText(R.string.dessert).toString(), R.color.fade_red));
        recipeArrayList.add(new ChildModelClass(R.drawable.img_drinks, getText(R.string.drink).toString(), R.color.fade_green));
        recipeArrayList.add(new ChildModelClass(R.drawable.img_fried, getText(R.string.fried).toString(), R.color.fade_yellow));
        recipeArrayList.add(new ChildModelClass(R.drawable.img_grilled, getText(R.string.grilled).toString(), R.color.fade_blue));
        recipeArrayList.add(new ChildModelClass(R.drawable.img_steamed, getText(R.string.steamed).toString(), R.color.fade_purple));

        parentModelClasses.add(new ParentModelClass(getText(R.string.recipes_category).toString(), recipeArrayList));
//        parentModelClasses.add(new ParentModelClass(getText(R.string.recipes_category).toString(), searchListItem));

        ParentAdapter parentAdapter = new ParentAdapter(parentModelClasses, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(parentAdapter);
        parentAdapter.notifyDataSetChanged();

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