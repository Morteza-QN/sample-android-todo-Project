package com.example.to_doproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {return new Task(source);}

        @Override
        public Task[] newArray(int size) {return new Task[size];}
    };
    private long    id;
    private String  title;
    private boolean completed;

    public Task() {
    }

    public Task(String title, boolean completed) {
        this.title     = title;
        this.completed = completed;
    }

    protected Task(Parcel in) {
        this.id        = in.readLong();
        this.title     = in.readString();
        this.completed = in.readByte() != 0;
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

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.completed ? (byte) 1 : (byte) 0);
    }
}
