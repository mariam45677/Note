package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.notes.Model.Note;
import com.example.notes.parse.NoteRepo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

public class MainActivity extends AppCompatActivity implements NoteRecyclerAdapter.OnNoteListener, View.OnClickListener {

    private RecyclerView rec;
    private ArrayList<Note> mNotes =new ArrayList<>();
    private NoteRecyclerAdapter mNoteRecycle;
    private NoteRepo mNoteRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rec = findViewById(R.id.Recyclar);
        findViewById(R.id.fab).setOnClickListener(this);
        mNoteRepo = new NoteRepo(this);
        //insertFakeNotes




     initRecycler();
     insertFake();
     retrieveNotes();



    }

    private void retrieveNotes() {
        mNoteRepo.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(mNotes.size() > 0){
                    mNotes.clear();
                }
                if(notes != null){
                    mNotes.addAll(notes);
                }
               mNoteRecycle.notifyDataSetChanged();
            }
        });
    }
    private void insertFake() {
        for (int i = 0; i < 1000; i++) {
           Note note = new Note();
            note.setTitle("title #" + i);
            note.setContent("content #: " + i);
         //   note.getTimestamp ("33 june");
            mNotes.add(note);


        }
        mNoteRecycle.notifyDataSetChanged();
    }

    private void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

       rec.setLayoutManager(linearLayoutManager);
        mNoteRecycle = new NoteRecyclerAdapter(mNotes,this);
        rec.setAdapter(mNoteRecycle);
        new ItemTouchHelper(itemTouchHlper).attachToRecyclerView(rec);



    }

    @Override
    public void onNoteClick(int position) {
       // Log.d(TAG , "dddddddddddddddddd" , + position);
        Intent intent = new Intent(this,NoyeActivity.class);
        intent.putExtra("select" , mNotes.get(position));
        startActivity(intent);



    }
    private void deleteNote(Note note){
      mNotes.remove(note);
      mNoteRecycle.notifyDataSetChanged();
    }
    private ItemTouchHelper.SimpleCallback itemTouchHlper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
            https://github.com/mariam45677/Note

        }
    };

    @Override
    public void onClick(View v) {
        Intent intent =new Intent(this,NoyeActivity.class);
        startActivity(intent);

    }
}
