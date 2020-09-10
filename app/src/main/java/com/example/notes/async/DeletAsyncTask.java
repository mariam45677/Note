package com.example.notes.async;

import android.os.AsyncTask;

import com.example.notes.Model.Note;
import com.example.notes.parse.NoteDao;

public class DeletAsyncTask extends AsyncTask<Note,Void,Void> {
    private NoteDao mNoteDao;

    public DeletAsyncTask (NoteDao dao) {
        mNoteDao = dao;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.delete(notes);

        return null;
    }
}
