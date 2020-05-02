package com.example.to_doproject;

public class Task {
    private long    id;
    private String  title;
    private boolean completed;

    public Task() {
    }

    public Task(String title, boolean completed) {
        this.title     = title;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
