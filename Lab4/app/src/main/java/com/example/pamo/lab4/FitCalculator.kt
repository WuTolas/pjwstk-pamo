package com.example.pamo.lab4

import kotlin.math.pow

object FitCalculator {

    /**
     * Calculates BMI.
     *
     * @param weight double - kilograms
     * @param height double - meters
     * @return double
     */
    fun calculateBmi(weight: Double, height: Double): Double {
        return weight / height.pow(2.0)
    }

    /**
     * Calculates body calories need.
     *
     * @param weight double - kilograms
     * @param height double - meters
     * @param gender Gender enum
     * @param age int
     * @return double
     */
    fun calculateBodyCaloriesNeed(weight: Double, height: Double, gender: Gender?, age: Int): Int {
        var result = 0.0
        when (gender) {
            Gender.MALE -> result = 66.47 + 13.7 * weight + 5 * height * 100 - 6.76 * age
            Gender.FEMALE -> result = 655.1 + 9.567 * weight + 1.85 * height * 100 - 4.68 * age
        }
        return result.toInt()
    }
}