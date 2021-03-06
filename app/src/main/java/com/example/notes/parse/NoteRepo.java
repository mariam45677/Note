package com.example.notes.parse;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notes.Model.Note;
import com.example.notes.async.InsertAsync;

import java.util.List;

public class NoteRepo {
    private NoteDataBase mNoteDataBase;
    public NoteRepo(Context context){
        mNoteDataBase = NoteDataBase.getInstance(context);
    }
    public void insertNoteTask(Note note){
        new InsertAsync(mNoteDataBase.getNoteDao()).execute(note);
    }

    public void updateNoteTask(Note note){
        //new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotesTask() {
        return mNoteDataBase.getNoteDao().getNotes();
    }

    public void deleteNoteTask(Note note){
      //  new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

}
