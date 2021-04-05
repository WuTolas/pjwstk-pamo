package com.example.pamo.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private TextView bmiValueOutput;
    private TextView bmiTypeOutput;
    private Button countButton;
    private Button clearButton;

    private BmiCalculator bmiCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();

        clearButton.setOnClickListener(V -> clearValues());
        countButton.setOnClickListener(V -> prepareCalculatedOutput());
    }

    private void initializeComponents() {
        bmiValueOutput = findViewById(R.id.bmiValueOutput);
        bmiTypeOutput = findViewById(R.id.bmiTypeOutput);
        editWeight = findViewById(R.id.editWeight);
        editHeight = findViewById(R.id.editHeight);
        countButton = findViewById(R.id.countButton);
        clearButton = findViewById(R.id.clearButton);

        bmiCalculator = new BmiCalculator();
    }

    private void prepareCalculatedOutput() {
        if (isValidated()) {
            bmiCalculator.setWeight(Double.parseDouble(editWeight.getText().toString()));
            bmiCalculator.setHeight(Double.parseDouble(editHeight.getText().toString()));
            double bmiValue = bmiCalculator.calculateBmi();

            BmiResultType bmiType = BmiResultType.getBmiResultByValue(bmiValue);
            bmiValueOutput.setText(String.format(Locale.getDefault(), "%f", bmiValue));
            bmiTypeOutput.setText(bmiType.getResourceId());
            bmiTypeOutput.setTextColor(getResources().getColor(bmiType.getColorId(), null));
        } else {
            clearResult();
        }
    }

    private void clearValues() {
        editWeight.setText(null);
        editHeight.setText(null);
        clearResult();

        editWeight.requestFocus();
    }

    private void clearResult() {
        bmiValueOutput.setText(null);
        bmiTypeOutput.setText(null);
    }

    private boolean isValidated() {
        int errorsCount = 0;

        if (editWeight.getText().toString().isEmpty()) {
            editWeight.setError(getString(R.string.e_fieldEmpty));
            errorsCount++;
        } else if (Double.parseDouble(editWeight.getText().toString()) <= 0) {
            editWeight.setError(getString(R.string.e_fieldPositive));
            errorsCount++;
        }

        if (editHeight.getText().toString().isEmpty()) {
            editHeight.setError(getString(R.string.e_fieldEmpty));
            errorsCount++;
        } else if (Double.parseDouble(editHeight.getText().toString()) <= 0) {
            editHeight.setError(getString(R.string.e_fieldPositive));
            errorsCount++;
        }

        return errorsCount == 0;
    }
}
