package com.example.team_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {

    private final WorkoutPlanDBHelper planHelper;

    public WorkoutDAO(Context context) {
        this.planHelper = new WorkoutPlanDBHelper(context);
    }

    public boolean saveWorkoutPlan(String programType, List<WorkoutEntry> workoutEntries) {
        SQLiteDatabase database = planHelper.getWritableDatabase();
        try {
            database.beginTransaction();

            // 기존 전체 삭제 후 새로 저장
            database.delete(DatabaseHelper.TABLE_WORKOUT, null, null);

            for (WorkoutEntry entry : workoutEntries) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_PROGRAM_TYPE, programType);
                values.put(DatabaseHelper.COLUMN_WEEK, entry.getWeek());
                values.put(DatabaseHelper.COLUMN_DAY_NAME, entry.getDayName());
                values.put(DatabaseHelper.COLUMN_EXERCISE_NAME, entry.getExerciseName());
                values.put(DatabaseHelper.COLUMN_SET_DETAIL, entry.getSetDetail());
                values.put(DatabaseHelper.COLUMN_DATE, entry.getDate());

                database.insertOrThrow(DatabaseHelper.TABLE_WORKOUT, null, values);
            }

            database.setTransactionSuccessful();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            if (database != null) database.endTransaction();
        }
    }

    public List<WorkoutEntry> getWorkoutEntriesByDate(String date) {
        List<WorkoutEntry> entries = new ArrayList<>();
        SQLiteDatabase db = planHelper.getReadableDatabase();

        android.database.Cursor cursor = db.query(
                DatabaseHelper.TABLE_WORKOUT,
                null,
                DatabaseHelper.COLUMN_DATE + "=?",
                new String[]{date},
                null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int week = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEEK));
                String dayName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DAY_NAME));
                String exName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_NAME));
                String setDetail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SET_DETAIL));
                String entryDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

                entries.add(new WorkoutEntry(week, dayName, exName, setDetail, entryDate));
            }
            cursor.close();
        }

        return entries;
    }

    public List<WorkoutEntry> getAllWorkoutEntries() {
        List<WorkoutEntry> entries = new ArrayList<>();
        SQLiteDatabase db = planHelper.getReadableDatabase();

        String sortOrder = DatabaseHelper.COLUMN_WEEK + " ASC, " +
                DatabaseHelper.COLUMN_ID + " ASC";

        android.database.Cursor cursor = db.query(
                DatabaseHelper.TABLE_WORKOUT,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int week = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEEK));
                String dayName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DAY_NAME));
                String exName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_NAME));
                String setDetail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SET_DETAIL));
                String entryDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

                entries.add(new WorkoutEntry(week, dayName, exName, setDetail, entryDate));
            }
            cursor.close();
        }

        return entries;
    }
}
