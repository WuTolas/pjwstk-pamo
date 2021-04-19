package com.example.pamo.lab3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pamo.lab3.BmiResultType;
import com.example.pamo.lab3.FitCalculator;
import com.example.pamo.lab3.R;

import java.io.InputStream;
import java.util.Locale;

public class BmiActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private TextView bmiValueOutput;
    private TextView bmiTypeOutput;
    private ImageView proposedFoodOutput;
    private Button countButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        initializeComponents();
        loadFormPreferences();

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
        proposedFoodOutput = findViewById(R.id.proposedFood);
    }

    private void prepareCalculatedOutput() {
        if (isValidated()) {
            double weight = Double.parseDouble(editWeight.getText().toString());
            double height = Double.parseDouble(editHeight.getText().toString());
            double bmi = FitCalculator.calculateBmi(weight, height);

            BmiResultType bmiType = BmiResultType.getBmiResultByValue(bmi);
            bmiValueOutput.setText(String.format(Locale.getDefault(), "%.2f", bmi));
            bmiTypeOutput.setText(bmiType.getResourceId());
            bmiTypeOutput.setTextColor(getResources().getColor(bmiType.getColorId(), null));
            proposedFoodOutput.setImageBitmap(getFoodBitmapFromAsset(bmiType));
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
        editor.apply();
    }

    private void loadFormPreferences() {
        SharedPreferences shared = getSharedPreferences("FORM_VALUES", Context.MODE_PRIVATE);
        editWeight.setText(shared.getString(getString(R.string.key_weight), null));
        editHeight.setText(shared.getString(getString(R.string.key_height), null));
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

        clearResult();
        clearFormPreferences();

        editWeight.requestFocus();
    }

    private void clearResult() {
        bmiValueOutput.setText(null);
        bmiTypeOutput.setText(null);
        proposedFoodOutput.setImageBitmap(null);
    }

    private Bitmap getFoodBitmapFromAsset(BmiResultType bmiType) {
        String bmiName = bmiType.toString().toLowerCase();
        String foodLocation = "food/" + bmiName;
        Bitmap result = null;
        try {
            String[] files = getAssets().list(foodLocation);
            int filePos = (int) (Math.random() * files.length);
            String pickedFood = foodLocation + "/" + files[filePos];
            InputStream in = getAssets().open(pickedFood);
            result = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception ex) {
            Log.w(this.getClass().getName(), "Couldn't get the picture.");
        }
        return result;
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

        return errorsCount == 0;
    }
}
