package com.example.team12.components.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.components.search.FragmentSearchNotFound;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    List<SectionClass> sectionList;
    Context context;

    public SectionAdapter(List<SectionClass> sectionList, Context context) {
        this.sectionList = sectionList;
        this.context = context;
    }

    @Override
    public SectionAdapter.SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_menu_section_item, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        holder.sectionName.setText(sectionList.get(position).sectionName);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to fragment daily section menu
                Toast.makeText(context, sectionList.get(position).sectionName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView sectionName;
        CardView cardView;
        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionName = itemView.findViewById(R.id.section_text);
            cardView = itemView.findViewById(R.id.section_cv);
        }
    }
}
