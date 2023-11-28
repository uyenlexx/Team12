package com.example.team12.components.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.team12.R;

public class FragmentBmiCalculator extends Fragment {
    Button btnCalculate;
    TextView resultText;
    TextView resultNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View bmiCalculatorView = inflater.inflate(R.layout.fragment_calculator_bmi, container, false);
        return bmiCalculatorView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCalculate = view.findViewById(R.id.calculate_button);
        resultText = view.findViewById(R.id.bmi_result_text);
        resultNumber = view.findViewById(R.id.bmi_result);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setVisibility(View.VISIBLE);
                resultNumber.setVisibility(View.VISIBLE);
            }
        });
    }
}