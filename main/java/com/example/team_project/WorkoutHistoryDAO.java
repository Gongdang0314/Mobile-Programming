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
                    result.add(new WorkoutHistory(lastDate, items, currentMemo));
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
            result.add(new WorkoutHistory(lastDate, items, currentMemo));
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
}
