package com.example.to_doproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDataAccessObject {
    @Insert
    long addTask(Task task);

    @Query("SELECT * FROM tbl_task")
    List<Task> getTasks();

    @Query("SELECT * FROM tbl_task WHERE title LIKE '%' || :query || '%'")
    List<Task> searchInTasks(String query);

    @Update
    int updateTask(Task task);

    @Delete
    int deleteTask(Task task);

    @Query("DELETE FROM tbl_task")
    void clearAllTasks();
}
