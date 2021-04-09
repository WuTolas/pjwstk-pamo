package com.example.pamo.lab2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.pamo.lab2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bmiActivityButton = (Button) findViewById(R.id.bt_activity_bmi);
        bmiActivityButton.setOnClickListener(V -> launchActivity(BmiActivity.class));

        Button bmrActivityButton = (Button) findViewById(R.id.bt_activity_bmr);
        bmrActivityButton.setOnClickListener(V -> launchActivity(BmrActivity.class));
    }

    private void launchActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}