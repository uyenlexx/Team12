package com.example.team12.components.menu;

import java.util.List;

public class SectionClass {
    String sectionName;
    List<RecipeClass> recipesList;

    public SectionClass(String sectionName, List<RecipeClass> recipesList) {
        this.sectionName = sectionName;
        this.recipesList = recipesList;
    }
}
