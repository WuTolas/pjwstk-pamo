package com.example.pamo.lab3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.pamo.lab3.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bmiActivityButton = findViewById(R.id.bt_activity_bmi);
        bmiActivityButton.setOnClickListener(V -> launchActivity(BmiActivity.class));

        Button bmrActivityButton = findViewById(R.id.bt_activity_bmr);
        bmrActivityButton.setOnClickListener(V -> launchActivity(BmrActivity.class));

        Button chartActivityButton = findViewById(R.id.bt_activity_chart);
        chartActivityButton.setOnClickListener(V -> launchActivity(ChartActivity.class));

        Button quizActivityButton = findViewById(R.id.bt_activity_quiz);
        quizActivityButton.setOnClickListener(V -> launchActivity(QuizActivity.class));
    }

    private void launchActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}