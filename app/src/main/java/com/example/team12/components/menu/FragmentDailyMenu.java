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
import com.example.team12.components.search.ParentAdapter;

import java.util.ArrayList;

public class FragmentDailyMenu extends Fragment {
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    ArrayList<SectionClass> sectionArrayList;
    ArrayList<RecipeClass> recipeArrayList;

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
        sectionArrayList = new ArrayList<>();
        recipeArrayList = new ArrayList<>();

        recipeArrayList.add(new RecipeClass(R.drawable.img_example_1, "Recipe 1", "500kcal"));
        recipeArrayList.add(new RecipeClass(R.drawable.img_example_2, "Recipe 2", "200kcal"));
        recipeArrayList.add(new RecipeClass(R.drawable.img_example_3, "Recipe 3", "347kcal"));

        sectionArrayList.add(new SectionClass("Daily Menu 1", recipeArrayList));

        recipeArrayList.clear();
        recipeArrayList.add(new RecipeClass(R.drawable.img_example_1, "Recipe 1", "500kcal"));
        recipeArrayList.add(new RecipeClass(R.drawable.img_example_2, "Recipe 2", "200kcal"));
        recipeArrayList.add(new RecipeClass(R.drawable.img_example_3, "Recipe 3", "347kcal"));

        sectionArrayList.add(new SectionClass("Daily Menu 2", recipeArrayList));

        SectionAdapter sectionAdapter = new SectionAdapter(sectionArrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(sectionAdapter);
        sectionAdapter.notifyDataSetChanged();
    }
}