package com.example.team_project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wendler531 implements WorkoutProgram {
    private static final double[][] WEEK_PERCENTAGES = {
            {0.65, 0.75, 0.85}, {0.70, 0.80, 0.90},
            {0.75, 0.85, 0.95}, {0.40, 0.50, 0.60}
    };
    private static final int[][] WEEK_REPS = {
            {5, 5, 5}, {3, 3, 3}, {5, 3, 1}, {5, 5, 5}
    };
    private static final String PROGRAM_TYPE = "WENDLER";

    @Override
    public List<WorkoutEntry> generateFull4WeekCalendar(Map<String, Exercise> exercises) {
        List<WorkoutEntry> fullCalendar = new ArrayList<>();

        for (int week = 1; week <= 4; week++) {
            String day1Name = "WEEK " + week + " - Day 1 (스쿼트)";
            fullCalendar.addAll(generateWeeklyWorkout(exercises.get("스쿼트"), week, day1Name));

            String day2Name = "WEEK " + week + " - Day 2 (벤치프레스)";
            fullCalendar.addAll(generateWeeklyWorkout(exercises.get("벤치프레스"), week, day2Name));

            double rowAccessoryWeight = WeightCalculator.PlateWeight(exercises.get("바벨로우").TM * 0.5);
            String setDetail = rowAccessoryWeight + "kg x 10회 x 5세트";
            fullCalendar.add(new WorkoutEntry(week, day2Name, "바벨 로우 (보조)", setDetail));

            fullCalendar.add(new WorkoutEntry(week, "WEEK " + week + " - DAY 3", "휴식", ""));

            String day4Name = "WEEK " + week + " - Day 4 (데드리프트)";
            fullCalendar.addAll(generateWeeklyWorkout(exercises.get("데드리프트"), week, day4Name));

            String day5Name = "WEEK " + week + " - Day 5 (OHP)";
            fullCalendar.addAll(generateWeeklyWorkout(exercises.get("오버헤드프레스"), week, day5Name));

            setDetail = rowAccessoryWeight + "kg x 10회 x 5세트";
            fullCalendar.add(new WorkoutEntry(week, day5Name, "바벨 로우 (보조)", setDetail));

            fullCalendar.add(new WorkoutEntry(week, "WEEK " + week + " - DAY 6", "휴식", ""));
            fullCalendar.add(new WorkoutEntry(week, "WEEK " + week + " - DAY 7", "휴식", ""));
        }
        return fullCalendar;
    }

    @Override
    public String getProgramType() {
        return PROGRAM_TYPE;
    }

    public List<WorkoutEntry> generateWeeklyWorkout(Exercise exercise, int week, String dayName) {
        List<WorkoutEntry> workout = new ArrayList<>();
        double tm = exercise.TM;
        String exerciseBaseName = exercise.exercise_name;

        double[] warmupPercents = {0.40, 0.50, 0.60};
        int[] warmupReps = {5, 5, 3};

        for (int i = 0; i < 3; i++) {
            double weight = WeightCalculator.PlateWeight(tm * warmupPercents[i]);
            String setDetail = "웜업 " + (i + 1) + ": " + weight + "kg x " + warmupReps[i] + "회";
            workout.add(new WorkoutEntry(week, dayName, exerciseBaseName + " (웜업)", setDetail));
        }

        for (int i = 0; i < 3; i++) {
            double percent = WEEK_PERCENTAGES[week - 1][i];
            int reps = WEEK_REPS[week - 1][i];
            double weight = WeightCalculator.PlateWeight(tm * percent);
            String repString = (i == 2 && week != 4) ? reps + "+회 (최소 " + reps + "회이상으로 가능한 최대 반복수)" : reps + "회";
            String setDetail = "세트 " + (i + 1) + ": " + weight + "kg x " + repString;
            workout.add(new WorkoutEntry(week, dayName, exerciseBaseName, setDetail));
        }

        double bbbWeight = WeightCalculator.PlateWeight(tm * 0.5);
        String setDetail = bbbWeight + "kg x 10회 x 5세트";
        workout.add(new WorkoutEntry(week, dayName, exerciseBaseName + " (보조)", setDetail));

        return workout;
    }
}
