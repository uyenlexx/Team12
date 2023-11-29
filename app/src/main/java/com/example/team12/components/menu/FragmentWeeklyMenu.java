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

import java.util.ArrayList;

public class FragmentWeeklyMenu extends Fragment implements RecipeAdapter.RecycleViewInterface {
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    ArrayList<SectionModelClass> sectionList;
    ArrayList<RecipeModelClass> recipesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View weeklyMenuView = inflater.inflate(R.layout.fragment_menu_weekly, container, false);
        return weeklyMenuView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frameLayout = view.findViewById(R.id.frame_layout_menu_weekly);
        recyclerView = view.findViewById(R.id.menu_weekly_rv);
        sectionList = new ArrayList<>();
        recipesList = new ArrayList<>();

        recipesList.add(new RecipeModelClass((R.drawable.img_example_1), "Monday", "Recipe 1", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_2), "Tuesday", "Recipe 2", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_3), "Wednesday", "Recipe 3", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_1), "Thursday", "Recipe 1", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_2), "Friday", "Recipe 2", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_3), "Saturday", "Recipe 3", "500kcal"));
        recipesList.add(new RecipeModelClass((R.drawable.img_example_3), "Sunday", "Recipe 3", "500kcal"));

        sectionList.add(new SectionModelClass(recipesList, "Weekly Menu 1"));

        SectionAdapter sectionAdapter = new SectionAdapter(sectionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeClick(RecipeModelClass recipeModelClass) {
        FragmentRecipeDetailed fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_menu_weekly);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_menu_daily, fragmentRecipeDetailed)
                .addToBackStack(null)
                .commit();
    }
}