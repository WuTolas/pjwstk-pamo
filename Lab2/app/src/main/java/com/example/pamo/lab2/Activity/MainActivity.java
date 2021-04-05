package com.example.pamo.lab2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import com.example.pamo.lab2.BmiResultType;
import com.example.pamo.lab2.FitCalculator;
import com.example.pamo.lab2.Gender;
import com.example.pamo.lab2.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button bmiActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bmiActivityButton = (Button) findViewById(R.id.bt_activity_bmi);
        bmiActivityButton.setOnClickListener(V -> launchBmiActivity());
    }

    private void launchBmiActivity() {
        Intent intent = new Intent(this, BmiActivity.class);
        startActivity(intent);
    }
}