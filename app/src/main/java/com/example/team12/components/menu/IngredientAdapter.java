package com.example.team12.components.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.team12.R;
import com.example.team12.entity.Ingredient;
import com.example.team12.entity.ListVariable;
import com.example.team12.entity.Recipe;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<IngredientModelClass> ingredientList;
    Context context;
    //    RecycleViewInterface recycleViewInterface;
    public IngredientAdapter(List<IngredientModelClass> ingredientList) {
        this.ingredientList = ingredientList;
//        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View recipeView = LayoutInflater.from(context)
                .inflate(R.layout.fragment_menu_recipe_item, parent, false);
        return new IngredientViewHolder(recipeView);
    }

    public void add(IngredientModelClass recipe) {
        ingredientList.add(recipe);
    }

    private void remove(int position) {
        if (position < 0) {
            System.out.println("Error: position number " + position);
        } else {
            ingredientList.remove(position);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if (ingredientList.get(position).url == null) {
            holder.ingredientImage.setImageResource(ingredientList.get(position).image);
        } else {
            Glide.with(context)
                    .load(ingredientList.get(position).getUrl())
                    .apply(new RequestOptions().override(300, 300))
                    .error(R.drawable.img_trending_1)
                    .into(holder.ingredientImage);
        }
//        holder.recipeImage.setImageResource(recipesList.get(position).image);
        holder.header.setText(ingredientList.get(position).header);
        holder.ingredientName.setText(ingredientList.get(position).ingredientName);
        holder.ingredientCalories.setText(ingredientList.get(position).ingredientCalories);
//        holder.recipeList.setLayoutManager(new LinearLayoutManager(
//                holder.itemView.getContext(), RecyclerView.VERTICAL, false
//        ));
//        holder.recipeList.setAdapter(recipesList.get(position).recipesInAMeal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientList.get(position).onClick(view);
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
                        .setTitle("Do you want to remove " + ingredientList.get(position).ingredientName + " from favorite list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int ingredientId = -1;
                                for (Ingredient ingredient: ListVariable.ingredientList.values()) {
                                    if (ingredient.getIngredientName().equals(ingredientList.get(position).ingredientName)) {
                                        ingredientId = ingredient.getIngredientId();
                                        break;
                                    }
                                }
                                ListVariable.currentUser.removeFavoriteIngredientFromFirebase(ingredientId);
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
        Log.d("RecipeAdapter", "getItemCount: " + ingredientList.size());
        return ingredientList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        CardView ingredientCard;
        ImageView ingredientImage;
        TextView header;
        TextView ingredientName;
        TextView ingredientCalories;
//        RecyclerView recipeList;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientCard = itemView.findViewById(R.id.item_cv);
            ingredientImage = itemView.findViewById(R.id.item_img);
            header = itemView.findViewById(R.id.item_header);
//            recipeList = itemView.findViewById(R.id.menu_meal_recipes);
            ingredientName = itemView.findViewById(R.id.item_name);
            ingredientCalories = itemView.findViewById(R.id.item_calories);
        }
    }

    public interface RecycleViewInterface {
        void onIngredientClick(IngredientModelClass recipe);
    }
}
