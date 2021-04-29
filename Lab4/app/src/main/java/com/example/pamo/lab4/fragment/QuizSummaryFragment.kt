package com.example.pamo.lab4.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pamo.lab4.R

class QuizSummaryFragment : Fragment() {

    private var score = 0
    private var questionsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            score = requireArguments().getInt(SCORE_PARAM)
            questionsCount = requireArguments().getInt(QUESTIONS_COUNT_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_summary, container, false)
        val summaryView = view.findViewById<TextView>(R.id.quiz_summary)
        summaryView.text = getString(R.string.t_summary_score, score, questionsCount)
        val resetButton = view.findViewById<Button>(R.id.btn_quiz_reset)
        resetButton.setOnClickListener(resetListener)
        return view
    }

    private val resetListener = View.OnClickListener { v: View? ->
        val quizFragment = requireActivity()
                .supportFragmentManager
                .findFragmentByTag("QUIZ_FRAGMENT") as QuizFragment?
        if (quizFragment != null) {
            requireActivity().supportFragmentManager
                    .popBackStackImmediate()
            quizFragment.startQuiz()
        }
    }

    companion object {
        private const val SCORE_PARAM = "score"
        private const val QUESTIONS_COUNT_PARAM = "questionsCount"
    }
}