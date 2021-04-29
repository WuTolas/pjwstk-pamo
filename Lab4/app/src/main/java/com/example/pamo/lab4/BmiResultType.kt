package com.example.pamo.lab4

enum class BmiResultType(val resourceId: Int, private val minValue: Double, private val maxValue: Double, val colorId: Int) {

    UNDERWEIGHT(R.string.t_underweight, 0.0, 18.5, R.color.teal_700),
    CORRECT(R.string.t_correctValue, 18.5, 25.0, R.color.green),
    OVERWEIGHT(R.string.t_overweight, 25.0, 30.0, R.color.orange),
    OBESITY(R.string.t_obesity, 30.0, 35.0, R.color.red),
    EXTREME_OBESITY(R.string.t_extremeObesity, 35.0, Double.MAX_VALUE, R.color.purple_700);

    companion object {
        fun getBmiResultByValue(value: Double): BmiResultType {
            return values().first { t: BmiResultType -> value >= t.minValue && value < t.maxValue }
        }
    }
}