package com.example.team12.components.menu;

import java.util.List;

public class SectionModelClass {
//    List<RecipeModelClass> recipeList;
    RecipeAdapter recipeList;
    String itemText;
    boolean isExpandable;

    public SectionModelClass(RecipeAdapter recipeList, String itemText) {
        this.recipeList = recipeList;
        this.itemText = itemText;
        this.isExpandable = false;
    }
}
