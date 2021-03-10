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

        bmiOutput = findViewById(R.id.bmiOutput);
        editWeight = findViewById(R.id.editWeight);
        editHeight = findViewById(R.id.editHeight);
        button = findViewById(R.id.button);

        button.setOnClickListener(V -> bmiOutput.setText(calculateBmi().toString()));
    }

    private Double calculateBmi() {
        double weight = Double.parseDouble(editWeight.getText().toString());
        double height = Double.parseDouble(editHeight.getText().toString());
        return weight / Math.pow(height, 2);
    }
}
