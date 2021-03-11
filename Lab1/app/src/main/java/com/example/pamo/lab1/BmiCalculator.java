package com.example.pamo.lab1;

public class BmiCalculator {

    private double weight;
    private double height;
    private double result;

    public Double calculateBmi() {
        result = weight / Math.pow(height, 2);
        return result;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getResult() {
        return result;
    }
}
