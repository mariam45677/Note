package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.notes.Model.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rec;
    private ArrayList<Note> mNotes =new ArrayList<>();
    private NoteRecyclerAdapter mNoteRecycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rec = findViewById(R.id.Recyclar);


     initRecycler();
     insertFake();


        
    }
    private void insertFake() {
        for (int i = 0; i < 1000; i++) {
           Note note = new Note();
            note.setTitle("title #" + i);
            note.setContact("content #: " + i);
            note.setTimes("Jan 2019");
            mNotes.add(note);


        }
        mNoteRecycle.notifyDataSetChanged();
    }

    private void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

       rec.setLayoutManager(linearLayoutManager);
        mNoteRecycle = new NoteRecyclerAdapter(mNotes);
        rec.setAdapter(mNoteRecycle);


    }
}
