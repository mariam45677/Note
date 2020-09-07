package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.notes.Model.Note;
import com.example.notes.parse.NoteRepo;

public class NoyeActivity extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, View.OnClickListener {
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    // UI components
    private LindEditText mLindEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBackArrow;


    // vars
    private boolean mIsNewNote;
    private Note mNoteInitial;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepo mNoteRepo;
    private Note mNoteFinal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noye);
        mLindEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        mCheckContainer=findViewById(R.id.check_container);
        mBackArrowContainer=findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);
        mCheck.setOnClickListener(this);
        mViewTitle.setOnClickListener(this);
        mNoteRepo = new NoteRepo(this);
        //mBackArrow.setOnClickListener(this);


        if(getIncomingIntent()){
            setNewNoteProperties();
            enableEditMode();
        }
        else {
            setNoteProperties();
            disableContentInteraction();
        }
        setListner();

        }

        private void setListner(){
        mLindEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mBackArrow.setOnClickListener(this);

    }
        private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selectNote")){
            //mNoteInitial = (Note) getIntent().getSerializableExtra("selectNote");
            mNoteInitial = getIntent().getParcelableExtra("selected_note");
            mMode =EDIT_MODE_DISABLED;
            mIsNewNote =false;
            return  false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;

    }
    private void saveChanges(){
        if(mIsNewNote){
           saveNewNote();
        }else{
            //updateNote();
        }
    }
    public void saveNewNote()
    {
        mNoteRepo.insertNoteTask(mNoteInitial);
    }

    private void setNewNoteProperties(){
        mViewTitle.setText("Note Title");
        mEditTitle.setText("Note Title");

        //mNoteFinal = new Note();
        //mNoteInitial = new Note();
        //mNoteInitial.setTitle("Note Title");
    }

    private void setNoteProperties(){
        mViewTitle.setText(mNoteInitial.getTitle());
        mEditTitle.setText(mNoteInitial.getTitle());
      //  mLindEditText.setText(mNoteInitial.getContact());

    }
    private void disableContentInteraction(){
        mLindEditText.setKeyListener(null);
        mLindEditText.setFocusable(false);
        mLindEditText.setFocusableInTouchMode(false);
        mLindEditText.setCursorVisible(false);
        mLindEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLindEditText.setKeyListener(new EditText(this).getKeyListener());
        mLindEditText.setFocusable(true);
        mLindEditText.setFocusableInTouchMode(true);
        mLindEditText.setCursorVisible(true);
        mLindEditText.requestFocus();
    }
    private void enableEditMode(){
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;

        enableContentInteraction();

    }

    private void disableEditMode(){
       // Log.d(TAG, "disableEditMode: called.");
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();
        saveChanges();
    }


@Override

    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {


    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        enableEditMode();

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
           case R.id.toolbar_back_arrow:{
                finish();
                break;
            }
            case R.id.toolbar_check:{
                disableEditMode();
                break;
            }
            case R.id.note_text_title:{
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;
            }
        }


    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED) {
            onClick(mCheck);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if(mMode == EDIT_MODE_ENABLED){
            enableEditMode();
    }
}}
