package com.example.team12.components;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.team12.R;
import com.example.team12.components.calculator.FragmentMealCalculator;
import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.search.ChildModelClass;
import com.example.team12.components.search.FragmentSearchNotFound;
import com.example.team12.components.search.ParentAdapter;
import com.example.team12.components.search.ParentModelClass;

import java.util.ArrayList;

public class FragmentSearch extends Fragment {
    SearchView searchBar;
    RecyclerView recyclerView;
    ArrayList<ParentModelClass> parentModelClasses;
    ArrayList<ChildModelClass> ingredientsArrayList;
    ArrayList<ChildModelClass> recipeArrayList;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

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
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //go to fragment search result
                frameLayout.removeAllViews();
//                FragmentSearchNotFound fragment = new FragmentSearchNotFound();
                FragmentRecipeDetailed fragment = new FragmentRecipeDetailed();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_search, fragment);
                fragmentTransaction.commit();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.search_recycler_view);
        ingredientsArrayList = new ArrayList<>();
        recipeArrayList = new ArrayList<>();
        parentModelClasses = new ArrayList<>();

        ingredientsArrayList.add(new ChildModelClass(R.drawable.img_fruits, getText(R.string.fruits).toString(), R.color.fade_red));
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

        ParentAdapter parentAdapter = new ParentAdapter(parentModelClasses, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(parentAdapter);
        parentAdapter.notifyDataSetChanged();
    }
}