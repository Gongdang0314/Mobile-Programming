package com.example.team_project;

import java.util.List;
import java.util.Map;

public interface WorkoutProgram {
    List<WorkoutEntry> generateFull4WeekCalendar(Map<String, Exercise> exercises);

    String getProgramType();
}
