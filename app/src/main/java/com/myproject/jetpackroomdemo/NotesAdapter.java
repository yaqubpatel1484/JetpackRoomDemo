package com.myproject.jetpackroomdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Context mContext;
    private ArrayList<Note> mNotesList;

    private OnDeleteClickListner deleteClickListner;

    interface OnDeleteClickListner{
        void onDelete(Note note);
    }

    NotesAdapter(Context context,OnDeleteClickListner clickListner){
        this.mContext = context;
        this.deleteClickListner = clickListner;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_notes_list_item,parent,false);

        return new NoteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        if (mNotesList != null){
            Note note = mNotesList.get(position);
            holder.setNotesData(note,position);
            holder.setListner();
        }else {
            holder.mTvNote.setText(mContext.getResources().getString(R.string.no_record_found));
        }


    }

    @Override
    public int getItemCount() {

        if(mNotesList != null){
            return mNotesList.size();
        }

        return 0;
    }


    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView mTvNote;
        ImageView mIvEdit, mIvDelete;
        Note note ;
        int pos;

         NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvNote = itemView.findViewById(R.id.tvNote);
            mIvEdit = itemView.findViewById(R.id.imageEdit);
            mIvDelete = itemView.findViewById(R.id.imageDelete);

        }

         void setNotesData(Note note, int position) {

             this.note = note;
             this.pos = position;
             mTvNote.setText(note.getNote());

        }

         void setListner() {

             mIvEdit.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent(mContext, EditNoteActivity.class);
                     intent.putExtra("NOTE_ID", mNotesList.get(pos).getNote_id());
                     ((Activity)mContext).startActivityForResult(intent,MainActivity.UPDATE_NOTE_REQUEST_CODE);

                 }
             });

             mIvDelete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                    deleteClickListner.onDelete(mNotesList.get(pos));
                 }
             });

        }
    }

     void setData(ArrayList<Note> mList){
        this.mNotesList = mList;
        notifyDataSetChanged();
    }

}
