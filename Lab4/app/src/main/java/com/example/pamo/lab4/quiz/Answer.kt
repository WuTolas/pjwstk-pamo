package com.example.pamo.lab4.quiz

import java.io.Serializable

class Answer : Serializable {
    var name: String? = null
    var isCorrect = false

    companion object {
        private const val serialVersionUID = 8458442933258502701L
    }
}