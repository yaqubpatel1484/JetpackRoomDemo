package com.myproject.jetpackroomdemo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel { //Here we extends AndroidViewModel instead of ViewModel to get Application Context

    private String TAG = getClass().getSimpleName();
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        NoteRoomDatabase noteRoomDatabase = NoteRoomDatabase.getInstance(application);
        mNoteDao = noteRoomDatabase.noteDao();
        mAllNotes = mNoteDao.getAllNotes(); //Method 2 : Code for fetch records

    }

    //*********************************************************
    // Code for insert record

    void insert(Note note) {
        new insertAsyncTask(mNoteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao mNoteDao;

        insertAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insertNote(notes[0]);
            return null;
        }
    }

    //*********************************************************
    // Code for fetch records
    // LiveData works in background threads that's why we don't need to do work with AsyncTask
    LiveData<List<Note>> getAllNotes() {
        // return mAllNotes = noteDao.getAllNotes(); //Method 1
        return mAllNotes;  //Method 2
    }


    //*********************************************************
    // Update record

    void updateRecord(Note note) {
        new UpdateAsyncTask(mNoteDao).execute(note);
    }

    public static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

         NoteDao noteDao;
        UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateRecord(notes[0]);
            return null;
        }
    }




    //*********************************************************
    //  Delete record
    void deleteRecord(Note note) {
        new DeleteAsyncTask().execute(note);
    }

     private class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

        /*
        *  For practice Here we declared DeleteAsyncTask class is non-static so it can access mNoteDao which is declared globally at class level
        *  above both asyncTask class are static that's why we pass mNoteDao through constructor and replace
        *  it with locally declared noteDoa object.
        * */

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.deleteRecord(notes[0]);
            return null;
        }
    }

    //*********************************************************
    // Code for clear ViewModel
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e(TAG, "MainActivity ViewMode Destroyed");
    }

}
