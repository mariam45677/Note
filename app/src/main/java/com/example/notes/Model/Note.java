package com.example.notes.Model;

import android.icu.text.CaseMap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "name")

public class Note implements Serializable {
    @ColumnInfo(name = "title")
    private String Title ;
    @NonNull

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "content")
    private String contact;

    @ColumnInfo(name = "timestamp")
    private String times;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note (String Title , String contact , String times) {
        this.Title = Title;
        this.contact = contact;
        this.times = times;
    }
    @Ignore
    public Note(){

    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }



}
