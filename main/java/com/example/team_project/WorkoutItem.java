package com.example.team_project;

public class WorkoutItem {
    private String name;

    private boolean done;
    private boolean notDone;

    private String setDetail;

    private String set1Info;
    private String set2Info;
    private String set3Info;

    public WorkoutItem(String name, String detail) {
        this.name = name;
        this.setDetail = detail;
        this.done = false;
        this.notDone = false;
    }

    public WorkoutItem(String name) {
        this.name = name;
        this.setDetail = "";
        this.done = false;
        this.notDone = false;
    }

    public String getName() { return name; }

    public boolean isDone() { return done; }
    public boolean isNotDone() { return notDone; }

    public void setDone(boolean v) {
        done = v;
        if (v) notDone = false;
    }

    public void setNotDone(boolean v) {
        notDone = v;
        if (v) done = false;
    }

    public String getSetDetail() { return setDetail; }
    public void setSetDetail(String detail) { this.setDetail = detail; }

    public String getSet1Info() { return set1Info; }
    public String getSet2Info() { return set2Info; }
    public String getSet3Info() { return set3Info; }

    public void setSet1Info(String info) { set1Info = info; }
    public void setSet2Info(String info) { set2Info = info; }
    public void setSet3Info(String info) { set3Info = info; }
}
