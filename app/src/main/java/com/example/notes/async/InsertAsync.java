package com.example.notes.async;

import android.os.AsyncTask;

import com.example.notes.Model.Note;
import com.example.notes.parse.NoteDao;

public class InsertAsync extends AsyncTask<Note, Void, Void> {
private NoteDao mNoteDao;

public InsertAsync (NoteDao dao) {
        mNoteDao = dao;
        }

@Override
protected Void doInBackground(Note... notes) {
        mNoteDao.insertNotes(notes);
        return null;
        }

        }

