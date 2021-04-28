package com.example.pamo.lab4.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pamo.lab4.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bmiActivityButton = findViewById<Button>(R.id.bt_activity_bmi)
        bmiActivityButton.setOnClickListener { launchActivity(BmiActivity::class.java) }
        val bmrActivityButton = findViewById<Button>(R.id.bt_activity_bmr)
        bmrActivityButton.setOnClickListener { launchActivity(BmrActivity::class.java) }
        val chartActivityButton = findViewById<Button>(R.id.bt_activity_chart)
        chartActivityButton.setOnClickListener { launchActivity(ChartActivity::class.java) }
        val quizActivityButton = findViewById<Button>(R.id.bt_activity_quiz)
        quizActivityButton.setOnClickListener { launchActivity(QuizActivity::class.java) }
    }

    private fun launchActivity(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}