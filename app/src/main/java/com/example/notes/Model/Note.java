package com.example.notes.Model;

import android.icu.text.CaseMap;

import java.io.Serializable;

public class Note implements Serializable {
    private String Title ,contact,times;

    public Note() {
        this.Title = Title;
        this.contact = contact;
        this.times = times;
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
