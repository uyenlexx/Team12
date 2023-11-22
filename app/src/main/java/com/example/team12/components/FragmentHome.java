package com.example.team12.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team12.R;
import com.example.team12.components.calculator.FragmentMealCalculator;
import com.example.team12.components.home.ItemClass;
import com.example.team12.components.home.MyAdapter;
import com.example.team12.components.search.ChildModelClass;
import com.example.team12.components.search.ParentModelClass;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    TextView helloTitle;
    TextView helloTitleDesc;
    RelativeLayout caloriesCalculatorContainer;
    RelativeLayout menuPlannerContainer;
    Button caloriesCalculatorButton;
    Button menuPlannerButton;
    RecyclerView recyclerView;
    ArrayList<ItemClass> items;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        return homeView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        helloTitle = view.findViewById(R.id.hello_title);
        helloTitleDesc = view.findViewById(R.id.hello_desc_title);
        caloriesCalculatorContainer = view.findViewById(R.id.calories_cal_container);
        menuPlannerContainer = view.findViewById(R.id.menu_builder_container);
        caloriesCalculatorButton = view.findViewById(R.id.calories_cal_btn);
        menuPlannerButton = view.findViewById(R.id.menu_builder_btn);

        caloriesCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMealCalculator fragment = new FragmentMealCalculator();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_main, fragment);
                fragmentTransaction.commit();
            }
        });

        menuPlannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMenu fragment = new FragmentMenu();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_main, fragment);
                fragmentTransaction.commit();
            }
        });

        recyclerView = view.findViewById(R.id.trending_recycler_view);
        items = new ArrayList<>();

        items.add(new ItemClass(R.drawable.img_trending_1, getText(R.string.trending_1).toString()));
        items.add(new ItemClass(R.drawable.img_trending_2, getText(R.string.trending_2).toString()));
        items.add(new ItemClass(R.drawable.img_trending_3, getText(R.string.trending_3).toString()));
        items.add(new ItemClass(R.drawable.img_trending_4, getText(R.string.trending_4).toString()));
        items.add(new ItemClass(R.drawable.img_trending_5, getText(R.string.trending_5).toString()));

        MyAdapter myAdapter = new MyAdapter(this.getContext(), items);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        myAdapter.notifyDataSetChanged();
    }
}
