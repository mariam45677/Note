package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.notes.Model.Note;

import java.util.ArrayList;
import java.util.NavigableMap;

public class MainActivity extends AppCompatActivity implements NoteRecyclerAdapter.OnNoteListener {

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


        }
    };
}
