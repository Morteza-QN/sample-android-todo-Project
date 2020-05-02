package com.example.to_doproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG           = "SQLiteHelper";
    private static final String TABLE_TASK    = "tbl_task";
    private static final String ID_TASK       = "id";
    private static final String TITLE_TASK    = "title";
    private static final String COMPLETE_TASK = "isComplete";


    public SQLiteHelper(@Nullable Context context) {
        super(context, "db_app", null, 1);
        Log.i(TAG, "SQLiteHelper: \ncreate data base db_app\n");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //call when unavailable db once
        try {
            db.execSQL(
                    " CREATE TABLE " + TABLE_TASK + " ( " + ID_TASK + " Integer PRIMARY KEY AUTOINCREMENT, " + TITLE_TASK +
                    " TEXT , " + COMPLETE_TASK + " BOOLEAN); ");
            Log.i(TAG, "onCreate: table" + TABLE_TASK);
        }
        catch (Exception e) {
            Log.i(TAG, "onCreate: not crated table \n\n" + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addTask(Task task) {
        SQLiteDatabase database      = getWritableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put(TITLE_TASK, task.getTitle());
        contentValues.put(COMPLETE_TASK, task.isCompleted());
        long result = database.insert(TABLE_TASK, null, contentValues); // error return -1
        database.close();
        return result;
    }

    public List<Task> getTasks() {
        Log.i(TAG, "getTasks: select \n\n");
        SQLiteDatabase database = getReadableDatabase();
        List<Task>     tasks    = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(" SELECT * FROM " + TABLE_TASK, null);
            Log.i(TAG, "getTasks: cursor \n\n" + cursor.toString());
            if (cursor.moveToFirst()) {
                do {
                    Task task = new Task();
                    task.setId(cursor.getLong(0));
                    task.setTitle(cursor.getString(1));
                    task.setCompleted(cursor.getInt(2) == 1);
                    tasks.add(task);
                    Log.i(TAG, "getTasks: size = " + tasks.size());
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

        }
        catch (Exception e) {
            Log.i(TAG, "getTasks: " + e.toString());
        }
        return tasks;
    }

    // TODO: 4/29/2020 searchInTasks
    public void searchInTasks(String query) {}


    public int updateTask(Task task) {
        SQLiteDatabase database      = getWritableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put(TITLE_TASK, task.getTitle());
        contentValues.put(COMPLETE_TASK, task.isCompleted());
        int result = database.update(TABLE_TASK, contentValues, " id = ? ",
                new String[]{String.valueOf(task.getId())}); // error return -1

        database.close();
        return result;
    }


    public int deleteTask(Task task) {
        SQLiteDatabase database = getWritableDatabase();
        int            result   = database.delete(TABLE_TASK, "id=?", new String[]{String.valueOf(task.getId())});

        database.close();
        return result;
    }

    public void deleteAllTasks() {}
}
