package com.example.team12.components.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.google.android.gms.common.data.DataHolder;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    List<SectionModelClass> mList;
    List<RecipeModelClass> recipeList;

    public SectionAdapter(List<SectionModelClass> mList) {
        this.mList = mList;
    }
    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menu_section_item, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        SectionModelClass sectionList = mList.get(position);
        holder.sectionTitle.setText(mList.get(position).itemText);

        boolean isExpandable = mList.get(position).isExpandable;
        holder.sectionExpandableList.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if (isExpandable)
            holder.expandArrow.setRotation(90);

        RecipeAdapter recipeAdapter = new RecipeAdapter(mList.get(position).recipeList);
        holder.sectionExpandableList.setLayoutManager(new LinearLayoutManager(
                holder.itemView.getContext(), RecyclerView.VERTICAL, false));
        holder.sectionExpandableList.setAdapter(recipeAdapter);
        holder.sectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionList.isExpandable = !(sectionList.isExpandable);
                mList.get(position).recipeList = sectionList.recipeList;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout sectionLayout;
        TextView sectionTitle;
        ImageView expandArrow;
        RecyclerView sectionExpandableList;
        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionLayout = itemView.findViewById(R.id.relative_layout);
            sectionTitle = itemView.findViewById(R.id.section_text);
            expandArrow = itemView.findViewById(R.id.section_arrow);
            sectionExpandableList = itemView.findViewById(R.id.expanded_rv);
        }
    }
}
