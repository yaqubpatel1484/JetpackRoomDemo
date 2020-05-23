package com.myproject.jetpackroomdemo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditNoteViewModel extends AndroidViewModel {

    private String TAG = getClass().getSimpleName();

    private NoteDao noteDao;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);

        NoteRoomDatabase noteRoomDatabase = NoteRoomDatabase.getInstance(application);
        noteDao = noteRoomDatabase.noteDao();

    }


    //*********************************************************
    // Code for fetch single record

     LiveData<Note> getRecord(String noteId){

        return noteDao.getRecord(noteId);
    }



    //*********************************************************
    // Code for clear ViewModel
    @Override
    protected void onCleared() {
        super.onCleared();

        Log.e(TAG, "Update ViewMode Destroyed");
    }
}
