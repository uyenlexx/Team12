package com.example.team12.components.home;


import android.view.View;

import com.example.team12.R;
import com.example.team12.components.menu.FragmentRecipeDetailed;

public class ItemClass implements View.OnClickListener {
    int image;
    String description;
    public ItemInterface itemInterface;
    public FragmentRecipeDetailed fragmentRecipeDetailed;

    public ItemClass(int image, String description) {
        this.image = image;
        this.description = description;
        this.fragmentRecipeDetailed = new FragmentRecipeDetailed(R.id.frame_layout_main);
    }

    @Override
    public void onClick(View view) {
        itemInterface.onClick(view, false);
//        System.out.println("fuck");
    }

    public void ItemInterfaceClick(ItemInterface itemInterface) {
        this.itemInterface = itemInterface;
//        System.out.println("fuck");
    }
}
