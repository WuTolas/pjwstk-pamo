package com.example.pamo.lab2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pamo.lab2.BmiResultType;
import com.example.pamo.lab2.FitCalculator;
import com.example.pamo.lab2.Gender;
import com.example.pamo.lab2.R;

import java.util.Locale;

public class BmiActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private EditText editAge;
    private RadioGroup editGender;
    private TextView bmiValueOutput;
    private TextView bmiTypeOutput;
    private TextView caloriesNeedOutput;
    private Button countButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        initializeComponents();

        clearButton.setOnClickListener(V -> clearValues());
        countButton.setOnClickListener(V -> prepareCalculatedOutput());
    }

    private void initializeComponents() {
        bmiValueOutput = findViewById(R.id.bmiValueOutput);
        bmiTypeOutput = findViewById(R.id.bmiTypeOutput);
        editGender = findViewById(R.id.genderRadioGroup);
        editGender.check(R.id.maleRadio);
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
            Gender gender = getGender();
            int age = Integer.parseInt(editAge.getText().toString());
            double bmi = FitCalculator.calculateBmi(weight, height);
            int calories = FitCalculator.calculateBodyCaloriesNeed(weight, height, gender, age);

            BmiResultType bmiType = BmiResultType.getBmiResultByValue(bmi);
            bmiValueOutput.setText(String.format(Locale.getDefault(), "%.2f", bmi));
            bmiTypeOutput.setText(bmiType.getResourceId());
            bmiTypeOutput.setTextColor(getResources().getColor(bmiType.getColorId(), null));
            caloriesNeedOutput.setText(String.format(Locale.getDefault(), "%d", calories));
        } else {
            clearResult();
        }
    }

    private void clearValues() {
        editWeight.setText(null);
        editWeight.setError(null);
        editHeight.setText(null);
        editHeight.setError(null);
        editAge.setText(null);
        editAge.setError(null);
        clearResult();

        editAge.requestFocus();
    }

    private void clearResult() {
        bmiValueOutput.setText(null);
        bmiTypeOutput.setText(null);
        caloriesNeedOutput.setText(null);
    }

    private Gender getGender() {
        int selectedRadioId = editGender.getCheckedRadioButtonId();
        Gender selectedGender = null;
        if (selectedRadioId == R.id.maleRadio) {
            selectedGender = Gender.MALE;
        } else if (selectedRadioId == R.id.femaleRadio) {
            selectedGender = Gender.FEMALE;
        }
        return selectedGender;
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
