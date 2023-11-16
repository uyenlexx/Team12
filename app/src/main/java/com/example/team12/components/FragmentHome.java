package com.example.team12.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        return homeView;


    }
}
