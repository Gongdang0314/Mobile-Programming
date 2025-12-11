package com.example.team_project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Madcow5x5 implements WorkoutProgram {
    static final double SET_INCREASE = 0.125;
    private static final String PROGRAM_TYPE = "MADCOW";

    @Override
    public List<WorkoutEntry> generateFull4WeekCalendar(Map<String, Exercise> exercises) {
        List<WorkoutEntry> fullCalendar = new ArrayList<>();

        for (int week = 1; week <= 4; week++) {

            String day1Name = "WEEK " + week + " - DAY 1 (HEAVY)";
            fullCalendar.addAll(generateWorkoutSets(exercises.get("스쿼트"), week, "HEAVY", day1Name));
            fullCalendar.addAll(generateWorkoutSets(exercises.get("벤치프레스"), week, "HEAVY", day1Name));
            fullCalendar.addAll(generateWorkoutSets(exercises.get("바벨로우"), week, "HEAVY", day1Name));

            fullCalendar.add(new WorkoutEntry(week, "WEEK " + week + " - DAY 2", "휴식", ""));

            String day3Name = "WEEK " + week + " - DAY 3 (L/M)";
            fullCalendar.addAll(generateWorkoutSets(exercises.get("스쿼트"), week, "LIGHT_MEDIUM", day3Name));
            fullCalendar.addAll(generateWorkoutSets(exercises.get("오버헤드프레스"), week, "LIGHT_MEDIUM", day3Name));
            fullCalendar.addAll(generateWorkoutSets(exercises.get("바벨로우"), week, "LIGHT_MEDIUM", day3Name));

            fullCalendar.add(new WorkoutEntry(week, "WEEK " + week + " - DAY 4", "휴식", ""));

            String day5Name = "WEEK " + week + " - DAY 5 (HLM)";
            fullCalendar.addAll(generateWorkoutSets(exercises.get("스쿼트"), week, "HEAVY_LIGHT_MEDIUM", day5Name));
            fullCalendar.addAll(generateWorkoutSets(exercises.get("벤치프레스"), week, "HEAVY_LIGHT_MEDIUM", day5Name));
            fullCalendar.addAll(generateWorkoutSets(exercises.get("바벨로우"), week, "HEAVY_LIGHT_MEDIUM", day5Name));

            double dlWeight = WeightCalculator.PlateWeight(exercises.get("데드리프트")._1RM * 0.85 * (1 + 0.025 * (week - 1)));
            String setDetail = "세트 1: " + dlWeight + "kg x 5회 (최고 중량)";
            fullCalendar.add(new WorkoutEntry(week, day5Name, "데드리프트 (1x5)", setDetail));

            fullCalendar.add(new WorkoutEntry(week, "WEEK " + week + " - DAY 6", "휴식", ""));
            fullCalendar.add(new WorkoutEntry(week, "WEEK " + week + " - DAY 7", "휴식", ""));
        }
        return fullCalendar;
    }

    @Override
    public String getProgramType() {
        return PROGRAM_TYPE;
    }

    public List<WorkoutEntry> generateWorkoutSets(Exercise exercise, int week, String dayType, String dayName) {
        List<WorkoutEntry> workout = new ArrayList<>();
        String pureExerciseName = exercise.exercise_name;

        double weekBaseWeight = exercise._1RM * 0.875;
        double currentTopSetWeight = weekBaseWeight * (1 + 0.025 * (week - 1));

        if (dayType.equals("HEAVY")) {
            double base = currentTopSetWeight;
            for (int s = 1; s <= 5; s++) {
                double weight = base * (1 - (5 - s) * SET_INCREASE);
                String setDetail;
                if (s == 5) {
                    setDetail = "세트 " + s + ": " + WeightCalculator.PlateWeight(base) + "kg x 5회 (최고 중량)";
                } else {
                    setDetail = "세트 " + s + ": " + WeightCalculator.PlateWeight(weight) + "kg x 5회";
                }
                workout.add(new WorkoutEntry(week, dayName, pureExerciseName, setDetail));
            }
        } else if (dayType.equals("LIGHT_MEDIUM")) {
            double finalTopSetWeight;

            if (exercise.exercise_name.equals("스쿼트")) {
                finalTopSetWeight = currentTopSetWeight * (1 - SET_INCREASE);
            } else {
                finalTopSetWeight = currentTopSetWeight * 0.8;
            }

            for (int s = 1; s <= 5; s++) {
                double weight = finalTopSetWeight * (1 - (5 - s) * SET_INCREASE);
                String setDetail;
                if (s == 5) {
                    setDetail = "세트 " + s + ": " + WeightCalculator.PlateWeight(finalTopSetWeight) + "kg x 5회 (최고 중량)";
                } else {
                    setDetail = "세트 " + s + ": " + WeightCalculator.PlateWeight(weight) + "kg x 5회";
                }
                workout.add(new WorkoutEntry(week, dayName, pureExerciseName, setDetail));
            }

        } else if (dayType.equals("HEAVY_LIGHT_MEDIUM")) {
            double base = currentTopSetWeight * (1 + 0.025);

            for (int s = 1; s <= 4; s++) {
                double weight = base * (1 - (5 - s) * SET_INCREASE);
                String setDetail = "세트 " + s + ": " + WeightCalculator.PlateWeight(weight) + "kg x 5회";
                workout.add(new WorkoutEntry(week, dayName, pureExerciseName, setDetail));
            }
            workout.add(new WorkoutEntry(week, dayName, pureExerciseName, "세트 5: " + WeightCalculator.PlateWeight(base) + "kg x 3회"));
            workout.add(new WorkoutEntry(week, dayName, pureExerciseName, "세트 6: " + WeightCalculator.PlateWeight(base * 0.8) + "kg x 8회 (백오프)"));
        }

        return workout;
    }
}
