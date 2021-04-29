package com.example.pamo.lab4.quiz

import java.io.Serializable
import java.util.*

class Question : Serializable {
    var name: String? = null
    var answers: MutableList<Answer> = ArrayList()

    companion object {
        private const val serialVersionUID = 8810718296371414610L
    }
}