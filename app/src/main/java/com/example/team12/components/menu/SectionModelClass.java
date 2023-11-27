package com.example.team12.components.menu;

import java.util.List;

public class SectionModelClass {
    List<RecipeModelClass> recipeList;
    String itemText;
    boolean isExpandable;

    public SectionModelClass(List<RecipeModelClass> recipeList, String itemText) {
        this.recipeList = recipeList;
        this.itemText = itemText;
        this.isExpandable = false;
    }
}
