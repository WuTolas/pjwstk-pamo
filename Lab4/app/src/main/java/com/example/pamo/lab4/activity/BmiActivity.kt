package com.example.pamo.lab4.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pamo.lab4.BmiResultType
import com.example.pamo.lab4.FitCalculator
import com.example.pamo.lab4.R
import java.util.*

class BmiActivity : AppCompatActivity() {

    private var editWeight: EditText? = null
    private var editHeight: EditText? = null
    private var bmiValueOutput: TextView? = null
    private var bmiTypeOutput: TextView? = null
    private var proposedFoodOutput: ImageView? = null
    private var countButton: Button? = null
    private var clearButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        initializeComponents()
        loadFormPreferences()
        clearButton!!.setOnClickListener { clearValues() }
        countButton!!.setOnClickListener { prepareCalculatedOutput() }
    }

    private fun initializeComponents() {
        bmiValueOutput = findViewById(R.id.bmiValueOutput)
        bmiTypeOutput = findViewById(R.id.bmiTypeOutput)
        editWeight = findViewById(R.id.editWeight)
        editHeight = findViewById(R.id.editHeight)
        countButton = findViewById(R.id.countButton)
        clearButton = findViewById(R.id.clearButton)
        proposedFoodOutput = findViewById(R.id.proposedFood)
    }

    private fun prepareCalculatedOutput() {
        if (isValidated) {
            val weight = editWeight!!.text.toString().toDouble()
            val height = editHeight!!.text.toString().toDouble()
            val bmi = FitCalculator.calculateBmi(weight, height)
            val bmiType = BmiResultType.getBmiResultByValue(bmi)
            bmiValueOutput!!.text = String.format(Locale.getDefault(), "%.2f", bmi)
            bmiTypeOutput!!.setText(bmiType.resourceId)
            bmiTypeOutput!!.setTextColor(resources.getColor(bmiType.colorId, null))
            proposedFoodOutput!!.setImageBitmap(getFoodBitmapFromAsset(bmiType))
            saveFormPreferences()
        } else {
            clearResult()
        }
    }

    private fun saveFormPreferences() {
        val shared = getSharedPreferences("FORM_VALUES", MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString(getString(R.string.key_weight), editWeight!!.text.toString())
        editor.putString(getString(R.string.key_height), editHeight!!.text.toString())
        editor.apply()
    }

    private fun loadFormPreferences() {
        val shared = getSharedPreferences("FORM_VALUES", MODE_PRIVATE)
        editWeight!!.setText(shared.getString(getString(R.string.key_weight), null))
        editHeight!!.setText(shared.getString(getString(R.string.key_height), null))
    }

    private fun clearFormPreferences() {
        val shared = getSharedPreferences("FORM_VALUES", MODE_PRIVATE)
        shared.edit().clear().apply()
    }

    private fun clearValues() {
        editWeight!!.text = null
        editWeight!!.error = null
        editHeight!!.text = null
        editHeight!!.error = null
        clearResult()
        clearFormPreferences()
        editWeight!!.requestFocus()
    }

    private fun clearResult() {
        bmiValueOutput!!.text = null
        bmiTypeOutput!!.text = null
        proposedFoodOutput!!.setImageBitmap(null)
    }

    private fun getFoodBitmapFromAsset(bmiType: BmiResultType): Bitmap? {
        val bmiName = bmiType.toString().toLowerCase(Locale.getDefault())
        val foodLocation = "food/$bmiName"
        var result: Bitmap? = null
        try {
            val files = assets.list(foodLocation)
            val filePos = (Math.random() * files!!.size).toInt()
            val pickedFood = foodLocation + "/" + files[filePos]
            val `in` = assets.open(pickedFood)
            result = BitmapFactory.decodeStream(`in`)
            `in`.close()
        } catch (ex: Exception) {
            Log.w(this.javaClass.name, "Couldn't get the picture.")
        }
        return result
    }

    private val isValidated: Boolean
        get() {
            var errorsCount = 0
            try {
                if (editWeight!!.text.toString().isEmpty()) {
                    editWeight!!.error = getString(R.string.e_fieldEmpty)
                    errorsCount++
                } else if (editWeight!!.text.toString().toDouble() <= 0) {
                    editWeight!!.error = getString(R.string.e_fieldPositive)
                    errorsCount++
                }
            } catch (ex: NumberFormatException) {
                editWeight!!.error = getString(R.string.e_notNumber)
                errorsCount++
            }
            try {
                if (editHeight!!.text.toString().isEmpty()) {
                    editHeight!!.error = getString(R.string.e_fieldEmpty)
                    errorsCount++
                } else if (editHeight!!.text.toString().toDouble() <= 0) {
                    editHeight!!.error = getString(R.string.e_fieldPositive)
                    errorsCount++
                }
            } catch (ex: NumberFormatException) {
                editHeight!!.error = getString(R.string.e_notNumber)
                errorsCount++
            }
            return errorsCount == 0
        }
}