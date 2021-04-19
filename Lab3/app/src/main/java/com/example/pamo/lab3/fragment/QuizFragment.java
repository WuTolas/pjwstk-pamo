package com.example.pamo.lab3.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pamo.lab3.R;
import com.example.pamo.lab3.quiz.Answer;
import com.example.pamo.lab3.quiz.Question;
import com.example.pamo.lab3.quiz.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizFragment extends Fragment {

    private static final String QUIZ_PARAM = "quiz";

    private final List<Button> answerButtons = new ArrayList<>();
    private Button nextButton;
    private Button summaryButton;
    private TextView questionCounterView;
    private TextView questionNameView;
    private TextView questionResultView;
    private int currentQuestionCount;
    private int correctAnswersCount;
    private String correctAnswer;
    private boolean initialised;

    private Quiz quiz;

    public QuizFragment() {
    }

    public static QuizFragment newInstance(Quiz quiz) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putSerializable(QUIZ_PARAM, quiz);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quiz = (Quiz) getArguments().getSerializable(QUIZ_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        answerButtons.clear();

        questionCounterView = view.findViewById(R.id.quiz_question_number);
        questionNameView = view.findViewById(R.id.quiz_question);
        questionResultView = view.findViewById(R.id.quiz_question_result);

        Button answerBtn1 = view.findViewById(R.id.btn_quiz_answer_1);
        answerBtn1.setOnClickListener(answerListener);
        Button answerBtn2 = view.findViewById(R.id.btn_quiz_answer_2);
        answerBtn2.setOnClickListener(answerListener);
        Button answerBtn3 = view.findViewById(R.id.btn_quiz_answer_3);
        answerBtn3.setOnClickListener(answerListener);
        Button answerBtn4 = view.findViewById(R.id.btn_quiz_answer_4);
        answerBtn4.setOnClickListener(answerListener);
        answerButtons.add(answerBtn1);
        answerButtons.add(answerBtn2);
        answerButtons.add(answerBtn3);
        answerButtons.add(answerBtn4);

        Button resetButton = view.findViewById(R.id.btn_quiz_reset);
        resetButton.setOnClickListener(l -> startQuiz());
        nextButton = view.findViewById(R.id.btn_quiz_next);
        nextButton.setOnClickListener(nextListener);
        summaryButton = view.findViewById(R.id.btn_quiz_summary);
        summaryButton.setOnClickListener(summaryListener);

        return view;
    }

    public void startQuiz() {
        resetBottomButtonsVisibility();
        Collections.shuffle(quiz.getQuestions());
        correctAnswersCount = 0;
        currentQuestionCount = 0;
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        nextButton.setEnabled(false);

        currentQuestionCount++;
        Question current = quiz.getQuestions().get(currentQuestionCount - 1);

        questionResultView.setText(null);

        questionCounterView.setText(getString(R.string.t_current_question_number, currentQuestionCount, quiz.getQuestions().size()));
        questionNameView.setText(current.getName());

        Collections.shuffle(current.getAnswers());
        for (int i = 0; i < current.getAnswers().size(); i++) {
            Answer answer = current.getAnswers().get(i);
            Button answerButton = answerButtons.get(i);
            resetButton(answerButton);
            answerButton.setText(answer.getName());
            if (answer.isCorrect()) {
                correctAnswer = answer.getName();
            }
        }
    }

    private void resetButton(Button btn) {
        btn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black));
        btn.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.white));
        btn.setEnabled(true);
    }

    private void resetBottomButtonsVisibility() {
        nextButton.setVisibility(View.VISIBLE);
        summaryButton.setVisibility(View.INVISIBLE);
    }

    private void disableAnswerButtons() {
        answerButtons.forEach(ab -> ab.setEnabled(false));
    }

    private void markCorrectButton() {
        answerButtons.stream()
                .filter(b -> b.getText().toString().equals(correctAnswer))
                .findFirst()
                .ifPresent(correctButton -> {
                    correctButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.green));
                    correctButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                });
    }

    private final OnClickListener answerListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button aButton = ((Button) v);
            String answer = aButton.getText().toString();

            if (answer.equals(correctAnswer)) {
                correctAnswersCount++;
                questionResultView.setText(R.string.t_correct_answer);
                questionResultView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green));
                aButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.green));
            } else {
                questionResultView.setText(R.string.t_incorrect_answer);
                questionResultView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red));
                aButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.red));
                markCorrectButton();
            }
            aButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));

            disableAnswerButtons();
            nextButton.setEnabled(true);
            summaryButton.setEnabled(true);
        }
    };

    private final OnClickListener nextListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = ((Button) v);
            if (currentQuestionCount + 1 == quiz.getQuestions().size()) {
                button.setVisibility(View.GONE);
                summaryButton.setVisibility(View.VISIBLE);
                summaryButton.setEnabled(false);
            }
            if (currentQuestionCount + 1 <= quiz.getQuestions().size()) {
                loadNextQuestion();
            }
        }
    };

    private final OnClickListener summaryListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("score", correctAnswersCount);
            bundle.putInt("questionsCount", quiz.getQuestions().size());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_quiz_view, QuizSummaryFragment.class, bundle, "QUIZ_SUMMARY_FRAGMENT")
                    .addToBackStack(null)
                    .commit();
        }
    };
}