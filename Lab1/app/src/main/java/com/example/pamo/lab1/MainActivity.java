package com.example.pamo.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private TextView bmiOutput;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();

        button.setOnClickListener(V -> {
            if (isValidated()) {
                bmiOutput.setText(calculateBmi().toString());
            }
        });
    }

    private Double calculateBmi() {
        double weight = Double.parseDouble(editWeight.getText().toString());
        double height = Double.parseDouble(editHeight.getText().toString());
        return weight / Math.pow(height, 2);
    }

    private void initializeComponents() {
        bmiOutput = findViewById(R.id.bmiOutput);
        editWeight = findViewById(R.id.editWeight);
        editHeight = findViewById(R.id.editHeight);
        button = findViewById(R.id.button);
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
