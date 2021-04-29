package com.example.pamo.lab4.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pamo.lab4.FitCalculator.calculateBodyCaloriesNeed
import com.example.pamo.lab4.Gender
import com.example.pamo.lab4.R
import java.util.*

class BmrActivity : AppCompatActivity() {

    private var editWeight: EditText? = null
    private var editHeight: EditText? = null
    private var editAge: EditText? = null
    private var editGender: RadioGroup? = null
    private var caloriesNeedOutput: TextView? = null
    private var countButton: Button? = null
    private var clearButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr)
        initializeComponents()
        loadFormPreferences()
        clearButton!!.setOnClickListener { V: View? -> clearValues() }
        countButton!!.setOnClickListener { V: View? -> prepareCalculatedOutput() }
    }

    private fun initializeComponents() {
        editGender = findViewById(R.id.genderRadioGroup)
        caloriesNeedOutput = findViewById(R.id.caloriesNeedOutput)
        editWeight = findViewById(R.id.editWeight)
        editHeight = findViewById(R.id.editHeight)
        editAge = findViewById(R.id.editAge)
        countButton = findViewById(R.id.countButton)
        clearButton = findViewById(R.id.clearButton)
    }

    private fun prepareCalculatedOutput() {
        if (isValidated) {
            val weight = editWeight!!.text.toString().toDouble()
            val height = editHeight!!.text.toString().toDouble()
            val gender = Gender.getGenderById(editGender!!.checkedRadioButtonId)
            val age = editAge!!.text.toString().toInt()
            val calories = calculateBodyCaloriesNeed(weight, height, gender, age)
            caloriesNeedOutput!!.text = String.format(Locale.getDefault(), "%d", calories)
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
        editor.putString(getString(R.string.key_age), editAge!!.text.toString())
        editor.putInt(getString(R.string.key_gender), editGender!!.checkedRadioButtonId)
        editor.apply()
    }

    private fun loadFormPreferences() {
        val shared = getSharedPreferences("FORM_VALUES", MODE_PRIVATE)
        editWeight!!.setText(shared.getString(getString(R.string.key_weight), null))
        editHeight!!.setText(shared.getString(getString(R.string.key_height), null))
        editAge!!.setText(shared.getString(getString(R.string.key_age), null))
        editGender!!.check(Gender.getGenderById(shared.getInt(getString(R.string.key_gender), R.id.maleRadio)).radioId)
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
        editAge!!.text = null
        editAge!!.error = null
        clearResult()
        clearFormPreferences()
        editAge!!.requestFocus()
    }

    private fun clearResult() {
        caloriesNeedOutput!!.text = null
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
            try {
                if (editAge!!.text.toString().isEmpty()) {
                    editAge!!.error = getString(R.string.e_fieldEmpty)
                    errorsCount++
                } else if (editAge!!.text.toString().toInt() <= 0) {
                    editAge!!.error = getString(R.string.e_fieldPositive)
                    errorsCount++
                }
            } catch (ex: NumberFormatException) {
                editAge!!.error = getString(R.string.e_notNumber)
                errorsCount++
            }
            return errorsCount == 0
        }
}