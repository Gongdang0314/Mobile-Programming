package com.example.team_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkoutHistoryDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "WorkoutHistory.db";
    public static final int DB_VERSION = 3;

    public static final String TABLE_NAME = "history";
    public static final String COL_ID = "id";
    public static final String COL_DATE = "date";
    public static final String COL_EXERCISE = "exercise";

    public static final String COL_DETAIL = "detail";

    public static final String COL_DONE = "done";

    public static final String COL_MEMO = "memo";

    public WorkoutHistoryDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_DATE + " TEXT, " +
                        COL_EXERCISE + " TEXT, " +
                        COL_DETAIL + " TEXT, " +
                        COL_DONE + " INTEGER, " +
                        COL_MEMO + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
