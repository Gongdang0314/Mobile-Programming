package com.example.team_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkoutPlanDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WorkoutPlan.db";
    private static final int DATABASE_VERSION = 1;

    public WorkoutPlanDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseHelper.SQL_CREATE_TABLE_WORKOUT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_WORKOUT);
        onCreate(db);
    }
}
