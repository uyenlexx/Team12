package com.example.team12.components.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    Context context;
    List<RecipeClass> recipes;

    public RecipeAdapter(Context context, List<RecipeClass> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_menu_recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipe_image.setImageResource(recipes.get(position).image);
        holder.recipe_name.setText(recipes.get(position).recipeName);
        holder.recipe_calories.setText(recipes.get(position).recipeCalories);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipe_image;
        TextView recipe_name;
        TextView recipe_calories;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipe_image = itemView.findViewById(R.id.item_img);
            recipe_name = itemView.findViewById(R.id.item_name);
            recipe_calories = itemView.findViewById(R.id.item_calories);
        }
    }
}
