package com.example.team_project;

public class WorkoutEntry {
    private int week;
    private String dayName;
    private String exerciseName;
    private String setDetail;
    private String date;  // ★ 추가됨

    // 🔵 기존에 필요한 5개짜리 생성자 (DAO에서 사용)
    public WorkoutEntry(int week, String dayName, String exerciseName, String setDetail, String date) {
        this.week = week;
        this.dayName = dayName;
        this.exerciseName = exerciseName;
        this.setDetail = setDetail;
        this.date = date;
    }

    // 🔵 ★ 오류 나는 것을 해결하는 새로운 4개짜리 생성자 추가
    //     → Madcow5x5 / Wendler531 에서 date 없이 생성할 때 사용됨
    public WorkoutEntry(int week, String dayName, String exerciseName, String setDetail) {
        this.week = week;
        this.dayName = dayName;
        this.exerciseName = exerciseName;
        this.setDetail = setDetail;
        this.date = "";  // 기본값 (비워두기)
    }

    public int getWeek() { return week; }
    public String getDayName() { return dayName; }
    public String getExerciseName() { return exerciseName; }
    public String getSetDetail() { return setDetail; }
    public String getDate() { return date; }
}
