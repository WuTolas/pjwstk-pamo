package com.example.pamo.lab4

enum class Gender(val radioId: Int) {
    MALE(R.id.maleRadio), FEMALE(R.id.femaleRadio);

    companion object {
        fun getGenderById(id: Int): Gender {
            return values().first { v: Gender -> v.radioId == id }
        }
    }
}