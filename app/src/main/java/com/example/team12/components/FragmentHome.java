package com.example.team12.components;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.components.calculator.FragmentMealCalculator;
import com.example.team12.components.home.ItemClass;
import com.example.team12.components.home.ItemInterface;
import com.example.team12.components.home.MyAdapter;
import com.example.team12.components.menu.FragmentRecipeDetailed;
import com.example.team12.components.search.ChildModelClass;
import com.example.team12.components.search.ParentModelClass;
import com.example.team12.entity.Ingredient;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class FragmentHome extends Fragment {
    TextView helloTitle;
    TextView helloTitleDesc;
    RelativeLayout caloriesCalculatorContainer;
    RelativeLayout menuPlannerContainer;
    Button caloriesCalculatorButton;
    Button menuPlannerButton;
    RecyclerView recyclerView;
    ArrayList<ItemClass> items;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        return homeView;
    }

    private void addItems(int image, Recipe recipe) {
        ItemClass itemClass = new ItemClass(image, recipe.getRecipeName());
        itemClass.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main, this);
        itemClass.ItemInterfaceClick(new ItemInterface() {
            @Override
            public void onClick(View view, boolean isLongPressed) {
                recipe.increaseViewCount();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_main, itemClass.fragmentRecipeDetailed)
                        .commit();
                Log.d("TAG", "onClick: " + recipe.getRecipeName());
            }
        });
        items.add(itemClass);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        System.out.println("fuck");
        CompletableFuture<Void> future = new CompletableFuture<>();
        helloTitle = view.findViewById(R.id.hello_title);
        helloTitle.setText("Hello " + ListVariable.currentUser.getName());
        helloTitleDesc = view.findViewById(R.id.hello_desc_title);
        caloriesCalculatorContainer = view.findViewById(R.id.calories_cal_container);
        menuPlannerContainer = view.findViewById(R.id.menu_builder_container);
        caloriesCalculatorButton = view.findViewById(R.id.calories_cal_btn);
        menuPlannerButton = view.findViewById(R.id.menu_builder_btn);
        Recipe.setUpFirebase();
        Ingredient.setUpFirebase();
        Recipe.getRecipeByViewCount();
        future.complete(null);
        caloriesCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMealCalculator fragment = new FragmentMealCalculator();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_main, fragment);
                fragmentTransaction.commit();

                bottomNavigationView.setSelectedItemId(R.id.calculate_tab);
            }
        });

        menuPlannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMenu fragment = new FragmentMenu();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_main, fragment);
                fragmentTransaction.commit();

                bottomNavigationView.setSelectedItemId(R.id.menu_tab);
            }
        });
        future.thenRun(() -> {
        recyclerView = view.findViewById(R.id.trending_recycler_view);
        items = new ArrayList<>();
        for (int i = 0; i < ListVariable.trendingRecipeList.size(); i++) {
            addItems(R.drawable.img_example_1, ListVariable.trendingRecipeList.get(i));
        }

//        items.add(new ItemClass(R.drawable.img_trending_1, getText(R.string.trending_1).toString()));
//        items.add(new ItemClass(R.drawable.img_trending_2, getText(R.string.trending_2).toString()));
//        items.add(new ItemClass(R.drawable.img_trending_3, getText(R.string.trending_3).toString()));
//        items.add(new ItemClass(R.drawable.img_trending_4, getText(R.string.trending_4).toString()));
//        items.add(new ItemClass(R.drawable.img_trending_5, getText(R.string.trending_5).toString()));

        MyAdapter myAdapter = new MyAdapter(this.getContext(), items);
        recyclerView.setAdapter(myAdapter);
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        myAdapter.notifyDataSetChanged();
    });

    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }
}
