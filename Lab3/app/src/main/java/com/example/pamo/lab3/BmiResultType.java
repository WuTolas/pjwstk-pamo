package com.example.pamo.lab3;

import java.util.stream.Stream;

public enum BmiResultType {

    UNDERWEIGHT(R.string.t_underweight, 0.0, 18.5, R.color.teal_700),
    CORRECT(R.string.t_correctValue, 18.5, 25.0, R.color.green),
    OVERWEIGHT(R.string.t_overweight, 25.0, 30.0, R.color.orange),
    OBESITY(R.string.t_obesity, 30.0, 35.0, R.color.red),
    EXTREME_OBESITY(R.string.t_extremeObesity, 35.0, Double.MAX_VALUE, R.color.purple_700);

    private final int resourceId;
    private final double minValue;
    private final double maxValue;
    private final int colorId;

    BmiResultType(int resourceId, double minValue, double maxValue, int colorId) {
        this.resourceId = resourceId;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.colorId = colorId;
    }

    public static BmiResultType getBmiResultByValue(double value) {
        return Stream.of(BmiResultType.values())
                .filter(t -> value >= t.minValue && value < t.maxValue)
                .findFirst()
                .orElse(null);
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getColorId() {
        return colorId;
    }
}
