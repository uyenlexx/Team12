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

import com.example.team12.R;

public class FragmentHome extends Fragment {
    TextView helloTitle;
    TextView helloTitleDesc;
    RelativeLayout caloriesCalculatorContainer;
    RelativeLayout menuPlannerContainer;
    Button caloriesCalculatorButton;
    Button menuPlannerButton;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        helloTitle = homeView.findViewById(R.id.hello_title);
        helloTitleDesc = homeView.findViewById(R.id.hello_desc_title);
        caloriesCalculatorContainer = homeView.findViewById(R.id.calories_cal_container);
        menuPlannerContainer = homeView.findViewById(R.id.menu_builder_container);
        caloriesCalculatorButton = homeView.findViewById(R.id.calories_cal_btn);
        menuPlannerButton = homeView.findViewById(R.id.menu_builder_btn);

        caloriesCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menuPlannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return homeView;
    }
}
