package com.example.team_project;

public class WorkoutEntry {
    private int week;
    private String dayName;
    private String exerciseName;
    private String setDetail;
    private String date;

    public WorkoutEntry(int week, String dayName, String exerciseName, String setDetail, String date) {
        this.week = week;
        this.dayName = dayName;
        this.exerciseName = exerciseName;
        this.setDetail = setDetail;
        this.date = date;
    }

    public WorkoutEntry(int week, String dayName, String exerciseName, String setDetail) {
        this.week = week;
        this.dayName = dayName;
        this.exerciseName = exerciseName;
        this.setDetail = setDetail;
        this.date = "";
    }

    public int getWeek() { return week; }
    public String getDayName() { return dayName; }
    public String getExerciseName() { return exerciseName; }
    public String getSetDetail() { return setDetail; }
    public String getDate() { return date; }
}
