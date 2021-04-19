package com.example.pamo.lab3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pamo.lab3.FitCalculator;
import com.example.pamo.lab3.Gender;
import com.example.pamo.lab3.R;

import java.util.Locale;

public class BmrActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private EditText editAge;
    private RadioGroup editGender;
    private TextView caloriesNeedOutput;
    private Button countButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr);
        initializeComponents();
        loadFormPreferences();

        clearButton.setOnClickListener(V -> clearValues());
        countButton.setOnClickListener(V -> prepareCalculatedOutput());
    }

    private void initializeComponents() {
        editGender = findViewById(R.id.genderRadioGroup);
        caloriesNeedOutput = findViewById(R.id.caloriesNeedOutput);
        editWeight = findViewById(R.id.editWeight);
        editHeight = findViewById(R.id.editHeight);
        editAge = findViewById(R.id.editAge);
        countButton = findViewById(R.id.countButton);
        clearButton = findViewById(R.id.clearButton);
    }

    private void prepareCalculatedOutput() {
        if (isValidated()) {
            double weight = Double.parseDouble(editWeight.getText().toString());
            double height = Double.parseDouble(editHeight.getText().toString());
            Gender gender = Gender.getGenderById(editGender.getCheckedRadioButtonId());
            int age = Integer.parseInt(editAge.getText().toString());
            int calories = FitCalculator.calculateBodyCaloriesNeed(weight, height, gender, age);

            caloriesNeedOutput.setText(String.format(Locale.getDefault(), "%d", calories));
            saveFormPreferences();
        } else {
            clearResult();
        }
    }

    private void saveFormPreferences() {
        SharedPreferences shared = getSharedPreferences("FORM_VALUES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(getString(R.string.key_weight), editWeight.getText().toString());
        editor.putString(getString(R.string.key_height), editHeight.getText().toString());
        editor.putString(getString(R.string.key_age), editAge.getText().toString());
        editor.putInt(getString(R.string.key_gender), editGender.getCheckedRadioButtonId());
        editor.apply();
    }

    private void loadFormPreferences() {
        SharedPreferences shared = getSharedPreferences("FORM_VALUES", Context.MODE_PRIVATE);
        editWeight.setText(shared.getString(getString(R.string.key_weight), null));
        editHeight.setText(shared.getString(getString(R.string.key_height), null));
        editAge.setText(shared.getString(getString(R.string.key_age), null));
        editGender.check(Gender.getGenderById(shared.getInt(getString(R.string.key_gender), R.id.maleRadio)).getRadioId());
    }

    private void clearFormPreferences() {
        SharedPreferences shared = getSharedPreferences("FORM_VALUES", Context.MODE_PRIVATE);
        shared.edit().clear().apply();
    }

    private void clearValues() {
        editWeight.setText(null);
        editWeight.setError(null);
        editHeight.setText(null);
        editHeight.setError(null);
        editAge.setText(null);
        editAge.setError(null);

        clearResult();
        clearFormPreferences();

        editAge.requestFocus();
    }

    private void clearResult() {
        caloriesNeedOutput.setText(null);
    }

    private boolean isValidated() {
        int errorsCount = 0;

        try {
            if (editWeight.getText().toString().isEmpty()) {
                editWeight.setError(getString(R.string.e_fieldEmpty));
                errorsCount++;
            } else if (Double.parseDouble(editWeight.getText().toString()) <= 0) {
                editWeight.setError(getString(R.string.e_fieldPositive));
                errorsCount++;
            }
        } catch (NumberFormatException ex) {
            editWeight.setError(getString(R.string.e_notNumber));
            errorsCount++;
        }

        try {
            if (editHeight.getText().toString().isEmpty()) {
                editHeight.setError(getString(R.string.e_fieldEmpty));
                errorsCount++;
            } else if (Double.parseDouble(editHeight.getText().toString()) <= 0) {
                editHeight.setError(getString(R.string.e_fieldPositive));
                errorsCount++;
            }
        } catch (NumberFormatException ex) {
            editHeight.setError(getString(R.string.e_notNumber));
            errorsCount++;
        }

        try {
            if (editAge.getText().toString().isEmpty()) {
                editAge.setError(getString(R.string.e_fieldEmpty));
                errorsCount++;
            } else if (Integer.parseInt(editAge.getText().toString()) <= 0) {
                editAge.setError(getString(R.string.e_fieldPositive));
                errorsCount++;
            }
        } catch (NumberFormatException ex) {
            editAge.setError(getString(R.string.e_notNumber));
            errorsCount++;
        }

        return errorsCount == 0;
    }
}