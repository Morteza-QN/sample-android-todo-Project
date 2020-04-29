package com.example.to_doproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG           = " SQLiteHelper ";
    private static final String TABLE_TASK    = " tbl_task ";
    private static final String TITLE_TASK    = " title ";
    private static final String COMPLETE_TASK = " isComplete ";


    public SQLiteHelper(@Nullable Context context) {
        super(context, "db_app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //call when unavailable db once
        try {
            db.execSQL("CREATE TABLE " + TABLE_TASK + " (id INTEGAR PRIMARY KEY AUTOINCREMENT, " + TITLE_TASK + " TEXT , " +
                       COMPLETE_TASK + " BOOLEAN);");

        }
        catch (Exception e) {
            Log.e(TAG, "onCreate: db \n" + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // TODO: 4/29/2020 addTask
    public long addTask(Task task) {
        SQLiteDatabase database      = getWritableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put(TITLE_TASK, task.getTitle());
        contentValues.put(COMPLETE_TASK, task.isCompleted());
        long result = database.insert(TABLE_TASK, null, contentValues); // error return -1
        database.close();
        return result;
    }

    // TODO: 4/29/2020 getTasks
    public void getTasks() {}

    // TODO: 4/29/2020 searchInTasks
    public void searchInTasks(String query) {}

    // TODO: 4/29/2020 updateTask
    public void updateTask(Task task) {}

    // TODO: 4/29/2020 deleteTask
    public void deleteTask() {}

    public void deleteAllTasks() {}
}
