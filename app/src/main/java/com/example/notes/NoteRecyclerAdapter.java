package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Model.Note;

import java.util.ArrayList;

  public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {
     private ArrayList<Note> mNotes = new ArrayList<>();
     private OnNoteListener monNoteListener;
     public  NoteRecyclerAdapter (ArrayList<Note> mNotes,OnNoteListener onNoteListener){
          this.mNotes = mNotes;
          this.monNoteListener = onNoteListener;

    }


      @NonNull
      @Override
      public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
          return new ViewHolder(view,monNoteListener);
      }

      @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.title.setText(mNotes.get(position).getTitle());
     holder.time.setText(mNotes.get(position).getTimes());

    }

    @Override
    public int getItemCount() {
       return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
     private TextView title,time;
     OnNoteListener monNoteListener;
        public ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            time = itemView.findViewById(R.id.note_timestamp);
            monNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            monNoteListener.onNoteClick(getAdapterPosition());

        }
    }
    public interface OnNoteListener{

            void onNoteClick(int position);
    }
}
