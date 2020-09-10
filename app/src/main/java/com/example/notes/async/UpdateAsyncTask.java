package com.example.notes.async;

import android.os.AsyncTask;

import com.example.notes.Model.Note;
import com.example.notes.parse.NoteDao;

import java.io.NotActiveException;

public class UpdateAsyncTask extends AsyncTask<Note,Void,Void> {
    private NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.updateNotes(notes);
        return null;
    }
}
