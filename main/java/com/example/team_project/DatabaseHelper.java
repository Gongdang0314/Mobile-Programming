package com.example.team_project;

public class DatabaseHelper {

    public static final String TABLE_WORKOUT = "workout_schedule";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WEEK = "week";
    public static final String COLUMN_DAY_NAME = "day_name";
    public static final String COLUMN_EXERCISE_NAME = "exercise_name";
    public static final String COLUMN_SET_DETAIL = "set_detail";
    public static final String COLUMN_PROGRAM_TYPE = "program_type";
    public static final String COLUMN_DATE = "date";

    public static final String SQL_CREATE_TABLE_WORKOUT =
            "CREATE TABLE " + TABLE_WORKOUT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PROGRAM_TYPE + " TEXT NOT NULL," +
                    COLUMN_WEEK + " INTEGER NOT NULL," +
                    COLUMN_DAY_NAME + " TEXT NOT NULL," +
                    COLUMN_EXERCISE_NAME + " TEXT NOT NULL," +
                    COLUMN_SET_DETAIL + " TEXT NOT NULL," +
                    COLUMN_DATE + " TEXT NOT NULL)";
}
