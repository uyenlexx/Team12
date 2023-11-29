package com.example.team12.components.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.team12.R;

public class FragmentBmiCalculator extends Fragment {
    Button btnCalculate;
    TextView resultText;
    TextView resultNumber;

    EditText heightInput;
    EditText weightInput;
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
        heightInput = view.findViewById(R.id.height_input);
        weightInput = view.findViewById(R.id.weight_input);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float height = Float.parseFloat(heightInput.getText().toString());
                Float weight = Float.parseFloat(weightInput.getText().toString());
                Float bmi = weight / (height * height);
                bmi = Math.round(bmi * 100.0) / 100.0f;
                resultNumber.setText(Float.toString(bmi));
                resultText.setVisibility(View.VISIBLE);
                resultNumber.setVisibility(View.VISIBLE);
            }
        });
    }
}