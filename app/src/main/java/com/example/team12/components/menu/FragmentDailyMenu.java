package com.example.team12.components.menu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.components.home.ItemClass;
import com.example.team12.components.home.ItemInterface;

import java.util.ArrayList;
import java.util.List;

public class FragmentDailyMenu extends Fragment implements RecipeAdapter.RecycleViewInterface {
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    Button newMenuButton;
    ArrayList<SectionModelClass> sectionList;
    SectionAdapter sectionAdapter;
//    ArrayList<RecipeModelClass> recipesList;
    private Fragment menuFragment;
    public FragmentDailyMenu(Fragment menuFragment) {
        this.menuFragment = menuFragment;
    }
    private FragmentDailyMenu getThis() {
        return this;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dailyMenuView = inflater.inflate(R.layout.fragment_menu_daily, container, false);
        frameLayout = dailyMenuView.findViewById(R.id.frame_layout_menu_daily);
        recyclerView = dailyMenuView.findViewById(R.id.menu_daily_rv);
        newMenuButton = dailyMenuView.findViewById(R.id.new_menu_btn);
        newMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater inflater = requireActivity().getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                View dialogView = inflater.inflate(R.layout.new_menu_name, null);
                EditText editText = dialogView.findViewById(R.id.new_menu_name);
                editText.setText("Daily Menu " + (sectionList.size() + 1));
                builder.setView(dialogView)
                        .setTitle("Choose your new menu name")
                        // Add action buttons
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...

//                                System.out.println(editText.getText().toString());
                                sectionList.add(new SectionModelClass(new RecipeAdapter(newMenu()), editText.getText().toString()));
                                sectionAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                LoginDialogFragment.this.getDialog().cancel();
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        sectionList = new ArrayList<>();
//        ArrayList<RecipeModelClass> recipesList = new ArrayList<>();
//
//        addRecipe(R.drawable.img_example_1, "Breakfast", "Recipe 1", "500kcal", recipesList);
//        addRecipe(R.drawable.img_example_2, "Lunch", "Recipe 2", "500kcal", recipesList);
//        addRecipe(R.drawable.img_example_3, "Dinner", "Recipe 3", "500kcal", recipesList);

        sectionList.add(new SectionModelClass(new RecipeAdapter(newMenu()), "Daily Menu 1"));
//        sectionList.add(new SectionModelClass(new RecipeAdapter(newMenu()), "Daily Menu 1"));

        sectionAdapter = new SectionAdapter(sectionList);
        sectionAdapter.setSectionAdapterTransaction(new SectionAdapter.SectionAdapterTransaction() {
            @Override
            public void onClick(View view, int position) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_main, new FragmentMiniSearch(menuFragment, sectionAdapter.mList.get(position).recipeList))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        sectionAdapter.notifyDataSetChanged();
        return dailyMenuView;
    }

    private void addMeal(int img, String header, String recipeName, ArrayList<RecipeModelClass> recipesList) {
        List<ItemClass> list = new ArrayList<>();
        ItemClass newRecipe = new ItemClass(img, recipeName);
        newRecipe.ItemInterfaceClick(new ItemInterface() {
            @Override
            public void onClick(View view, boolean isLongPressed) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_main, newRecipe.fragmentRecipeDetailed)
                        .commit();
            }
        });
        list.add(newRecipe);
        RecipeModelClass newMeal = new RecipeModelClass(img, header, recipeName, "550kcal");
//        newRecipe.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main, menuFragment);
//        newRecipe.RedirectRecipeModel(new RecipeModelRedirectInterface() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout_main, newRecipe.fragmentRecipeDetailed)
//                        .commit();
//            }
//        });
        recipesList.add(newMeal);
    }

    private ArrayList<RecipeModelClass> newMenu() {
        ArrayList<RecipeModelClass> newRecipeList = new ArrayList<>();
        addMeal(R.drawable.img_example_1, "Breakfast", "Recipe 1", newRecipeList);
        addMeal(R.drawable.img_example_2, "Lunch", "Recipe 2",  newRecipeList);
        addMeal(R.drawable.img_example_3, "Dinner", "Recipe 3", newRecipeList);
        return newRecipeList;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onRecipeClick(RecipeModelClass recipeModelClass) {
        FragmentRecipeDetailed fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_menu_daily, this);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_menu_daily, fragmentRecipeDetailed)
                .addToBackStack(null)
                .commit();
    }
}