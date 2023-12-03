package com.example.team12.components.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.team12.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    List<SearchResult> searchResultList;
    Context context;
    public SearchResultAdapter(List<SearchResult> searchResultList, Context context) {
        this.context = context;
        this.searchResultList = searchResultList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchResult searchResult = searchResultList.get(position);
        holder.itemName.setText(searchResult.getName());
        holder.itemDescription.setText(searchResult.getDescription());
        Glide.with(context)
                .load(searchResult.getUrl())
                .apply(new RequestOptions().override(300, 300))
                .error(R.drawable.img_trending_1)
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResultList.get(position).onClick(view);
            }
        });
    }

    public void setSearchResultList(List<SearchResult> searchResultList) {
        this.searchResultList = new ArrayList<>();
        this.searchResultList.addAll(searchResultList);
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView itemName;
        TextView itemDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_img);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);
        }
    }
}
