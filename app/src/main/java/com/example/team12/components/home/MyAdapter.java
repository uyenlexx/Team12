package com.example.team12.components.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.entity.IngredientList;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//    Context context;
    List<ItemClass> items;

    public MyAdapter(List<ItemClass> items) {
//        this.context = context;
        this.items = items;
    }

    public MyAdapter(ArrayList<IngredientList> searchListItem, Context context) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_child_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recipe_image.setImageResource(items.get(position).image);
        holder.description.setText(items.get(position).description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("on bind view");
                items.get(position).onClick(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recipe_image;
        TextView description;
        public MyViewHolder(View itemView) {
            super(itemView);
            recipe_image = itemView.findViewById(R.id.home_rv_item_img);
            description = itemView.findViewById(R.id.home_rv_item_desc);
        }
    }
}
