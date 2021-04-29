package com.example.pamo.lab4.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pamo.lab4.R
import com.example.pamo.lab4.fragment.QuizFragment
import com.example.pamo.lab4.quiz.Quiz
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.nio.charset.StandardCharsets

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val json = loadJSONFromAssets("quiz/dietary.json")
        var quiz: Quiz? = null
        try {
            val mapper = ObjectMapper()
            if (json != null) {
                quiz = mapper.readValue(json, Quiz::class.java)
            }
        } catch (ex: Exception) {
            Log.w(this.javaClass.name, "Couldn't parse json file.")
        }
        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putSerializable("quiz", quiz)
            supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_quiz_view, QuizFragment::class.java, bundle, "QUIZ_FRAGMENT")
                    .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        val quizFragment = supportFragmentManager
                .findFragmentById(R.id.fragment_quiz_view) as QuizFragment?
        quizFragment?.startQuiz()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun loadJSONFromAssets(fileName: String): String? {
        var json: String? = null
        try {
            val `is` = assets.open(fileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            Log.w(this.javaClass.name, "Couldn't load json file.")
        }
        return json
    }
}