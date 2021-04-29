package com.example.pamo.lab4.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pamo.lab4.R
import com.example.pamo.lab4.quiz.Quiz
import java.util.*
import java.util.function.Consumer

class QuizFragment : Fragment() {

    private val answerButtons: MutableList<Button> = ArrayList()
    private var nextButton: Button? = null
    private var summaryButton: Button? = null
    private var questionCounterView: TextView? = null
    private var questionNameView: TextView? = null
    private var questionResultView: TextView? = null
    private var currentQuestionCount = 0
    private var correctAnswersCount = 0
    private var correctAnswer: String? = null
    private var quiz: Quiz? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            quiz = requireArguments().getSerializable(QUIZ_PARAM) as Quiz?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        answerButtons.clear()
        questionCounterView = view.findViewById(R.id.quiz_question_number)
        questionNameView = view.findViewById(R.id.quiz_question)
        questionResultView = view.findViewById(R.id.quiz_question_result)
        val answerBtn1 = view.findViewById<Button>(R.id.btn_quiz_answer_1)
        answerBtn1.setOnClickListener(answerListener)
        val answerBtn2 = view.findViewById<Button>(R.id.btn_quiz_answer_2)
        answerBtn2.setOnClickListener(answerListener)
        val answerBtn3 = view.findViewById<Button>(R.id.btn_quiz_answer_3)
        answerBtn3.setOnClickListener(answerListener)
        val answerBtn4 = view.findViewById<Button>(R.id.btn_quiz_answer_4)
        answerBtn4.setOnClickListener(answerListener)
        answerButtons.add(answerBtn1)
        answerButtons.add(answerBtn2)
        answerButtons.add(answerBtn3)
        answerButtons.add(answerBtn4)
        val resetButton = view.findViewById<Button>(R.id.btn_quiz_reset)
        resetButton.setOnClickListener { startQuiz() }
        nextButton = view.findViewById(R.id.btn_quiz_next)
        nextButton!!.setOnClickListener(nextListener)
        summaryButton = view.findViewById(R.id.btn_quiz_summary)
        summaryButton!!.setOnClickListener(summaryListener)
        return view
    }

    fun startQuiz() {
        resetBottomButtonsVisibility()
        quiz!!.questions.shuffle()
        correctAnswersCount = 0
        currentQuestionCount = 0
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        nextButton!!.isEnabled = false
        currentQuestionCount++
        val current = quiz!!.questions[currentQuestionCount - 1]
        questionResultView!!.text = null
        questionCounterView!!.text = getString(R.string.t_current_question_number, currentQuestionCount, quiz!!.questions.size)
        questionNameView!!.text = current.name
        current.answers.shuffle()
        for (i in current.answers.indices) {
            val answer = current.answers[i]
            val answerButton = answerButtons[i]
            resetButton(answerButton)
            answerButton.text = answer.name
            if (answer.isCorrect) {
                correctAnswer = answer.name
            }
        }
    }

    private fun resetButton(btn: Button) {
        btn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
        btn.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.white))
        btn.isEnabled = true
    }

    private fun resetBottomButtonsVisibility() {
        nextButton!!.visibility = View.VISIBLE
        summaryButton!!.visibility = View.INVISIBLE
    }

    private fun disableAnswerButtons() {
        answerButtons.forEach(Consumer { ab: Button -> ab.isEnabled = false })
    }

    private fun markCorrectButton() {
        answerButtons.stream()
                .filter { b: Button -> b.text.toString() == correctAnswer }
                .findFirst()
                .ifPresent { correctButton: Button ->
                    correctButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.green))
                    correctButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                }
    }

    private val answerListener = View.OnClickListener { v ->
        val aButton = v as Button
        val answer = aButton.text.toString()
        if (answer == correctAnswer) {
            correctAnswersCount++
            questionResultView!!.setText(R.string.t_correct_answer)
            questionResultView!!.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green))
            aButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.green))
        } else {
            questionResultView!!.setText(R.string.t_incorrect_answer)
            questionResultView!!.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red))
            aButton.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.red))
            markCorrectButton()
        }
        aButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
        disableAnswerButtons()
        nextButton!!.isEnabled = true
        summaryButton!!.isEnabled = true
    }

    private val nextListener = View.OnClickListener { v ->
        val button = v as Button
        if (currentQuestionCount + 1 == quiz!!.questions.size) {
            button.visibility = View.GONE
            summaryButton!!.visibility = View.VISIBLE
            summaryButton!!.isEnabled = false
        }
        if (currentQuestionCount + 1 <= quiz!!.questions.size) {
            loadNextQuestion()
        }
    }

    private val summaryListener = View.OnClickListener {
        val bundle = Bundle()
        bundle.putInt("score", correctAnswersCount)
        bundle.putInt("questionsCount", quiz!!.questions.size)
        requireActivity().supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_quiz_view, QuizSummaryFragment::class.java, bundle, "QUIZ_SUMMARY_FRAGMENT")
                .addToBackStack(null)
                .commit()
    }

    companion object {
        private const val QUIZ_PARAM = "quiz"
    }
}