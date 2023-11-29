package com.example.team12.entity;

public class IngredientList {
    public String url;
    public String description;
    public String name;
    public IngredientList(String name, String description, String url) {
        this.description = description;
        this.name = name;
        this.url = url;
    }

    public IngredientList(){

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }


}
