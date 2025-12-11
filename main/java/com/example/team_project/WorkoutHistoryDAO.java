package com.example.team_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class WorkoutHistoryDAO {

    private final WorkoutHistoryDBHelper helper;

    public WorkoutHistoryDAO(Context context) {
        helper = new WorkoutHistoryDBHelper(context);
    }

    public void insert(String date, WorkoutItem item, String memo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WorkoutHistoryDBHelper.COL_DATE, date);
        values.put(WorkoutHistoryDBHelper.COL_EXERCISE, item.getName());
        values.put(WorkoutHistoryDBHelper.COL_DETAIL, item.getSetDetail());
        values.put(WorkoutHistoryDBHelper.COL_DONE, item.isDone() ? 1 : 0);
        values.put(WorkoutHistoryDBHelper.COL_MEMO, memo);

        db.insert(WorkoutHistoryDBHelper.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<WorkoutHistory> getAll() {

        ArrayList<WorkoutHistory> result = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                WorkoutHistoryDBHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WorkoutHistoryDBHelper.COL_DATE + " DESC"
        );

        String lastDate = "";
        ArrayList<WorkoutItem> items = null;
        String currentMemo = "";

        while (cursor.moveToNext()) {

            String date = cursor.getString(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_DATE));
            String exercise = cursor.getString(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_EXERCISE));
            String detail = cursor.getString(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_DETAIL));
            boolean done = cursor.getInt(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_DONE)) == 1;
            String memo = cursor.getString(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_MEMO));

            if (!date.equals(lastDate)) {
                if (items != null) {
                    int total = items.size();
                    int comp = 0;
                    for (WorkoutItem it : items) if (it.isDone()) comp++;

                    int rate = total == 0 ? 0 : (int)((comp * 100.0) / total);

                    WorkoutHistory wh = new WorkoutHistory(lastDate, items, currentMemo);
                    wh.achievement = rate;

                    result.add(wh);
                }

                items = new ArrayList<>();
                lastDate = date;
                currentMemo = memo;
            }

            WorkoutItem item = new WorkoutItem(exercise, detail);
            item.setDone(done);

            items.add(item);
        }

        if (items != null) {
            int total = items.size();
            int comp = 0;
            for (WorkoutItem it : items) if (it.isDone()) comp++;

            int rate = total == 0 ? 0 : (int)((comp * 100.0) / total);

            WorkoutHistory wh = new WorkoutHistory(lastDate, items, currentMemo);
            wh.achievement = rate;

            result.add(wh);
        }

        cursor.close();
        db.close();
        return result;
    }

    public void deleteByDate(String date) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(WorkoutHistoryDBHelper.TABLE_NAME, WorkoutHistoryDBHelper.COL_DATE + "=?", new String[]{date});
        db.close();
    }
    public WorkoutHistory getByDate(String date) {

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                WorkoutHistoryDBHelper.TABLE_NAME,
                null,
                WorkoutHistoryDBHelper.COL_DATE + " = ?",
                new String[]{date},
                null,
                null,
                null
        );

        ArrayList<WorkoutItem> items = new ArrayList<>();
        String memo = "";

        while (cursor.moveToNext()) {

            String exercise = cursor.getString(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_EXERCISE));
            String detail = cursor.getString(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_DETAIL));
            boolean done = cursor.getInt(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_DONE)) == 1;
            memo = cursor.getString(cursor.getColumnIndexOrThrow(WorkoutHistoryDBHelper.COL_MEMO));

            WorkoutItem item = new WorkoutItem(exercise, detail);
            item.setDone(done);

            items.add(item);
        }

        cursor.close();
        db.close();

        cursor.close();
        db.close();

        int total = items.size();
        int completed = 0;

        for (WorkoutItem it : items) {
            if (it.isDone()) completed++;
        }

        int rate = total == 0 ? 0 : (int)((completed * 100.0) / total);

        WorkoutHistory history = new WorkoutHistory(date, items, memo);
        history.achievement = rate;

        return history;

    }

}
