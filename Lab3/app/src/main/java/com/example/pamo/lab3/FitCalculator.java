package com.example.pamo.lab3;

public class FitCalculator {

    /**
     * Calculates BMI.
     *
     * @param weight double - kilograms
     * @param height double - meters
     * @return double
     */
    public static double calculateBmi(double weight, double height) {
        return weight / Math.pow(height, 2);
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
    public static int calculateBodyCaloriesNeed(double weight, double height, Gender gender, int age) {
        double result = 0;
        switch (gender) {
            case MALE:
                result = 66.47 + (13.7 * weight) + (5 * height * 100) - (6.76 * age);
                break;
            case FEMALE:
                result = 655.1 + (9.567 * weight) + (1.85 * height * 100) - (4.68 * age);
                break;
        }
        return (int) result;
    }
}
