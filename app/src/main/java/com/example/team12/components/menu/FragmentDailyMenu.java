package com.example.team12.components.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentDailyMenu extends Fragment {
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    ArrayList<SectionModelClass> sectionList;
    ArrayList<RecipeModelClass> recipesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dailyMenuView = inflater.inflate(R.layout.fragment_menu_daily, container, false);
        return dailyMenuView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = view.findViewById(R.id.frame_layout_menu);
        recyclerView = view.findViewById(R.id.menu_daily_rv);
        sectionList = new ArrayList<>();
        recipesList = new ArrayList<>();

        recipesList.add(new RecipeModelClass((R.drawable.img_example_1), "Breakfast", "Recipe 1", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_2), "Lunch", "Recipe 2", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_3), "Dinner", "Recipe 3", "500kcal"));

        sectionList.add(new SectionModelClass(recipesList, "Daily Menu 1"));

        SectionAdapter sectionAdapter = new SectionAdapter(sectionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        sectionAdapter.notifyDataSetChanged();
    }
}