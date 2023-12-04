package com.example.team12.components.search;

import android.graphics.Color;

import androidx.cardview.widget.CardView;

public class ChildModelClass {
    int icon;
    String category_item;
    int category_bg_color;
    public String url;
    public String description;
    public String name;
    public ChildModelClass(String name, String description, String url) {
        this.description = description;
        this.name = name;
        this.url = url;
    }

    public ChildModelClass() {
        this.description = "";
        this.name = "";
        this.url = "";

    }

    public ChildModelClass(int icon, String category_item, int category_bg_color) {
        this.icon = icon;
        this.category_item = category_item;
        this.category_bg_color = category_bg_color;
    }
}
