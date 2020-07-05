package com.example.notes.parse;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Model.Note;

import java.util.List;

@Dao

public interface NoteDao {

    @Insert
    long [] insertNotes (Note... notes);
    @Query("SELECT * FROM name")
    LiveData<List<Note>> getNotes();
    @Delete
    int delete(Note... notes);
    @Update
    int updateNotes(Note... notes);



}
