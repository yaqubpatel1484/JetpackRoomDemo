package com.myproject.jetpackroomdemo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    // here in Dao (Data Access Object) we can write our functions for CURD operations


    /*  Insert Method
    * Note: Methods annotated with @Insert can return either void, long, Long, long[], Long[] or List<Long>.
    * */
    @Insert
     void insertNote(Note note);

    /*  Fetch Data  */
    @Query("SELECT * fROM notes") // Room perform compile time check so this statement checked at compile time.
    LiveData<List<Note>> getAllNotes();

    /* Fetch single record */
    @Query("Select * from notes where note_id = :noteId")
    LiveData<Note> getRecord(String noteId);


    /* Update record */
    @Update
    void updateRecord(Note note);

    /* Delete record */
    @Delete
    void deleteRecord(Note note);
}
