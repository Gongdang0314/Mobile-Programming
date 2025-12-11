package com.example.team_project;

import java.util.List;

public class WorkoutHistory {
    public String date;
    public List<WorkoutItem> exercises;
    public int achievement;
    public String memo;

    public WorkoutHistory(String date, List<WorkoutItem> exercises,String memo) {
        this.date = date;
        this.exercises = exercises;
        this.memo = memo;
    }
}
