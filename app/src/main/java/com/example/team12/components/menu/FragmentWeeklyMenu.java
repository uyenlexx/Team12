package com.example.team12.components.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWeeklyMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWeeklyMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FrameLayout frameLayout;
    RecyclerView recyclerView;
    ArrayList<SectionModelClass> sectionList;
    ArrayList<RecipeModelClass> recipeList;
    private Fragment menuFragment;

    public FragmentWeeklyMenu() {
        // Required empty public constructor
    }

    public FragmentWeeklyMenu(Fragment menuFragment) {
        this.menuFragment = menuFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMealCalculator.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWeeklyMenu newInstance(String param1, String param2) {
        FragmentWeeklyMenu fragment = new FragmentWeeklyMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void addRecipe(int img, String header, String recipeName, String recipeCalories) {
        RecipeModelClass newRecipe = new RecipeModelClass(img, header, recipeName, recipeCalories);
        newRecipe.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main, menuFragment);
        newRecipe.RedirectRecipeModel(new RecipeModelRedirectInterface() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_main, newRecipe.fragmentRecipeDetailed)
                        .commit();
            }
        });
        recipeList.add(newRecipe);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = view.findViewById(R.id.frame_layout_menu);
        recyclerView = view.findViewById(R.id.menu_weekly_rv);

        recipeList = new ArrayList<>();
        addRecipe(R.drawable.img_example_1, "Breakfast", "Recipe 1", "500kcal");
        addRecipe(R.drawable.img_example_2, "Lunch", "Recipe 2", "500kcal");
        addRecipe(R.drawable.img_example_3, "Dinner", "Recipe 3", "500kcal");

        sectionList = new ArrayList<>();
        String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            sectionList.add(new SectionModelClass(new RecipeAdapter(recipeList), days[i]));
        }
        SectionAdapter sectionAdapter = new SectionAdapter(sectionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_weekly, container, false);
    }
}