package com.example.pamo.lab3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pamo.lab3.fragment.QuizFragment;
import com.example.pamo.lab3.R;
import com.example.pamo.lab3.quiz.Quiz;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String json = loadJSONFromAssets("quiz/dietary.json");
        Quiz quiz = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (json != null) {
                quiz = mapper.readValue(json, Quiz.class);
            }
        } catch (Exception ex) {
            Log.w(this.getClass().getName(), "Couldn't parse json file.");
        }

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("quiz", quiz);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_quiz_view, QuizFragment.class, bundle, "QUIZ_FRAGMENT")
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        QuizFragment quizFragment = (QuizFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_quiz_view);
        if (quizFragment != null) {
            quizFragment.startQuiz();
        }
    }

    @Override
    public void onBackPressed() {
        QuizActivity.this.finish();
    }

    private String loadJSONFromAssets(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Log.w(this.getClass().getName(), "Couldn't load json file.");
        }
        return json;
    }
}