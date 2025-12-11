package com.example.team_project;

public class Exercise {
    public String exercise_name;
    public double _1RM;
    public double TM;
    public String UPPER_LOWER;

    public Exercise(String exercise_name, double _1RM, String UPPER_LOWER) {
        this.exercise_name = exercise_name;
        this._1RM = _1RM;
        this.TM = _1RM * 0.9;
        this.UPPER_LOWER = UPPER_LOWER;
    }
}
