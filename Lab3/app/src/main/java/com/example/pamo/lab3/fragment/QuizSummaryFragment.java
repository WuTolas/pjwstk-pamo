package com.example.pamo.lab3.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pamo.lab3.R;

public class QuizSummaryFragment extends Fragment {


    private static final String SCORE_PARAM = "score";
    private static final String QUESTIONS_COUNT_PARAM = "questionsCount";

    private int score;
    private int questionsCount;

    public QuizSummaryFragment() {
    }

    public static QuizSummaryFragment newInstance(int score, int questionsCount) {
        QuizSummaryFragment fragment = new QuizSummaryFragment();
        Bundle args = new Bundle();
        args.putInt(SCORE_PARAM, score);
        args.putInt(QUESTIONS_COUNT_PARAM, questionsCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt(SCORE_PARAM);
            questionsCount = getArguments().getInt(QUESTIONS_COUNT_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_summary, container, false);

        TextView summaryView = view.findViewById(R.id.quiz_summary);
        summaryView.setText(getString(R.string.t_summary_score, score, questionsCount));

        Button resetButton = view.findViewById(R.id.btn_quiz_reset);
        resetButton.setOnClickListener(resetListener);
        return view;
    }

    private final OnClickListener resetListener = v -> {
        QuizFragment quizFragment = (QuizFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentByTag("QUIZ_FRAGMENT");

        if (quizFragment != null) {
            requireActivity().getSupportFragmentManager()
                    .popBackStackImmediate();
            quizFragment.startQuiz();
        }
    };
}