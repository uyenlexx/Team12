package com.example.team12.components.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.team12.R;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<RecipeModelClass> recipesList;
    Context context;
//    RecycleViewInterface recycleViewInterface;
    public RecipeAdapter(List<RecipeModelClass> recipesList) {
        this.recipesList = recipesList;
//        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View recipeView = LayoutInflater.from(context)
                .inflate(R.layout.fragment_menu_recipe_item, parent, false);
        return new RecipeViewHolder(recipeView);
    }

    public void add(RecipeModelClass recipe) {
        recipesList.add(recipe);
    }

    private void remove(int position) {
        if (position < 0) {
            System.out.println("Error: position number " + position);
        } else {
            recipesList.remove(position);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (recipesList.get(position).url == null) {
            holder.recipeImage.setImageResource(recipesList.get(position).image);
        } else {
            Glide.with(context)
                    .load(recipesList.get(position).getUrl())
                    .apply(new RequestOptions().override(300, 300))
                    .error(R.drawable.img_trending_1)
                    .into(holder.recipeImage);
        }
//        holder.recipeImage.setImageResource(recipesList.get(position).image);
        holder.header.setText(recipesList.get(position).header);
        holder.recipeName.setText(recipesList.get(position).recipeName);
        holder.recipeCalories.setText(recipesList.get(position).recipeCalories);
//        holder.recipeList.setLayoutManager(new LinearLayoutManager(
//                holder.itemView.getContext(), RecyclerView.VERTICAL, false
//        ));
//        holder.recipeList.setAdapter(recipesList.get(position).recipesInAMeal);
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Do you want to remove " + recipesList.get(position).recipeName + " from favorite list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int recipeId = -1;
                                for (Recipe recipe: ListVariable.recipeList.values()) {
                                    if (recipe.getRecipeName().equals(recipesList.get(position).recipeName)) {
                                        recipeId = recipe.getRecipeId();
                                        break;
                                    }
                                }
                                ListVariable.currentUser.removeFavoriteRecipeFromFirebase(recipeId);
                                remove(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
                return false;
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
//        RecyclerView recipeList;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeCard = itemView.findViewById(R.id.item_cv);
            recipeImage = itemView.findViewById(R.id.item_img);
            header = itemView.findViewById(R.id.item_header);
//            recipeList = itemView.findViewById(R.id.menu_meal_recipes);
            recipeName = itemView.findViewById(R.id.item_name);
            recipeCalories = itemView.findViewById(R.id.item_calories);
        }
    }

    public interface RecycleViewInterface {
        void onRecipeClick(RecipeModelClass recipe);
    }
}
