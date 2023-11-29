package com.example.team12.components.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.entity.IngredientList;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {
//    List<IngredientList> parentModelClassList;
    Context context;
    ArrayList<IngredientList> searchListItem;

    public SearchItemAdapter(ArrayList<IngredientList> searchListItem, Context context) {
        this.searchListItem = searchListItem;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchView = LayoutInflater.from(context)
                .inflate(R.layout.search_item, parent, false);
        return new ViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientList ingredientList = searchListItem.get(position);
        holder.itemName.setText(ingredientList.getName());
        holder.itemDescription.setText(ingredientList.getDescription());
        Glide.with(context)
                .load(ingredientList.getUrl())
                .apply(new RequestOptions().override(300, 300))
                .error(R.drawable.img_trending_1)
                .into(holder.itemImg);

    }

    @Override
    public int getItemCount() {
        return searchListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImg;
        TextView itemName, itemDescription;
        String url;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.item_img);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);

}
    }
}
