package com.example.team12.components.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<RecipeModelClass> recipesList;
//    RecycleViewInterface recycleViewInterface;
    public RecipeAdapter(List<RecipeModelClass> recipesList) {
        this.recipesList = recipesList;
//        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menu_recipe_item, parent, false);
        return new RecipeViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeImage.setImageResource(recipesList.get(position).image);
        holder.header.setText(recipesList.get(position).header);
        holder.recipeName.setText(recipesList.get(position).recipeName);
        holder.recipeCalories.setText(recipesList.get(position).recipeCalories);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipesList.get(position).onClick(view);
//        holder.recipeCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recycleViewInterface.onRecipeClick(recipesList.get(position));
            }
        });
    }

    @Override
    public int getItemCount()  {
        Log.d("RecipeAdapter", "getItemCount: " + recipesList.size());
        return recipesList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        CardView recipeCard;
        ImageView recipeImage;
        TextView header;
        TextView recipeName;
        TextView recipeCalories;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeCard = itemView.findViewById(R.id.item_cv);
            recipeImage = itemView.findViewById(R.id.item_img);
            header = itemView.findViewById(R.id.item_header);
            recipeName = itemView.findViewById(R.id.item_name);
            recipeCalories = itemView.findViewById(R.id.item_calories);
        }
    }

    public interface RecycleViewInterface {
        void onRecipeClick(RecipeModelClass recipe);
    }
}
