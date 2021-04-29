package com.example.pamo.lab4.quiz

import java.io.Serializable
import java.util.*

class Quiz : Serializable {
    var name: String? = null
    var questions: MutableList<Question> = ArrayList()

    companion object {
        private const val serialVersionUID = 3379200832549055538L
    }
}