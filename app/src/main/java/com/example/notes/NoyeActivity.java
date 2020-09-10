package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
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

public class NoyeActivity extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;
    private static final String TAG ="NoteActivity" ;

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
        mBackArrow.setOnClickListener(this);
        setListner();


        if(getIncomingIntent()){
            setNewNoteProperties();
            enableEditMode();
        }
        else {
            setNoteProperties();
            disableContentInteraction();
        }

        }
    private void saveChanges(){
        if(mIsNewNote){
            saveNewNote();
        }else{
            updateNote();
        }
    }
    public void updateNote() {
        mNoteRepo.updateNoteTask(mNoteFinal);
    }
    public void saveNewNote()
    {
        Log.d("TAG", "saveNewNote: "+mNoteInitial.getId()+"title"+mNoteInitial.getTitle());

        mNoteRepo.insertNoteTask(mNoteFinal);
        //you must add final note because initial when click btn will be null and finalnote will be has value
        //  mNoteRepo.insertNoteTask(mNoteInitial);
    }

        private void setListner(){
        mLindEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);

    }
        private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selectNote")){
            //mNoteInitial = (Note) getIntent().getSerializableExtra("selectNote");
            mNoteInitial = getIntent().getParcelableExtra("selected_note");
            mNoteFinal = new Note();
            mNoteFinal.setTitle(mNoteInitial.getTitle());
            mNoteFinal.setContent(mNoteInitial.getContent());
            mNoteFinal.setTimestamp(mNoteInitial.getTimestamp());
            mNoteFinal.setId(mNoteInitial.getId());
            mMode =EDIT_MODE_ENABLED;
            mIsNewNote =false;
            return  false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;

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
         Log.d(TAG, "disableEditMode: called.");
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();
        String temp = mLindEditText.getText().toString();
        temp = temp.replace("\n","");
        temp = temp.replace("" ,"");
        if (temp.length() > 0) {

            mNoteFinal.setTitle(mEditTitle.getText().toString());
            mNoteFinal.setContent(mLindEditText.getText().toString());
            String timestemp =Utility.getCurrentTimeStamp();
            mNoteFinal.setTimestamp(timestemp);
            Log.d(TAG, "disableEditMode: initial: " + mNoteInitial.toString());
            Log.d(TAG, "disableEditMode: final: " + mNoteFinal.toString());
            if(!mNoteFinal.getContent().equals(mNoteInitial.getContent()) || !mNoteFinal.getTitle().equals(mNoteInitial.getTitle())){
                Log.d("Tag", "disableEditMode: called?");

                saveChanges();

            }
        }
    }


    private void setNewNoteProperties(){
        mViewTitle.setText("Note Title");
        mEditTitle.setText("Note Title");


        mNoteFinal = new Note();
        mNoteInitial = new Note();
        mNoteInitial.setTitle("Note Title");

    }

    private void setNoteProperties(){
        mViewTitle.setText(mNoteInitial.getTitle());
        mEditTitle.setText(mNoteInitial.getTitle());
        mLindEditText.setText(mNoteInitial.getContent());


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
        Log.d(TAG, "onDoubleTap: double tapped.");

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
}

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mViewTitle.setText(charSequence.toString());

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
