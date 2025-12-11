package com.example.team_project;

public class WeightCalculator {

    public static double PlateWeight(double weight) {
        return Math.round(weight / 2.5) * 2.5;
    }

    public static double calculateEstimated1RM(double weight, int reps) {
        double estimated1RM = (reps == 1) ? weight : weight * (1 + reps / 30.0);
        return PlateWeight(estimated1RM);
    }
}
