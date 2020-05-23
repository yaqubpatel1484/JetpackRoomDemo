package com.myproject.jetpackroomdemo;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class,version = 1)  // here we are passing version of schema and entities
public abstract class NoteRoomDatabase extends RoomDatabase {


    //here we need to add list of Data Access Objects (Dao)
    public abstract NoteDao noteDao();

    private static volatile NoteRoomDatabase noteRoomDatabase;
   /* Volatile keyword is used to modify the value of a variable by different threads.
    It is also used to make classes thread safe. It means that multiple threads can use a method and instance of
     the classes at the same time without any problem.
    */

    //  we should have only on database instance that's why our database class should be  singleton
     static NoteRoomDatabase getInstance(Context context){

        if(noteRoomDatabase == null){

            noteRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    NoteRoomDatabase.class,"note_database")
                    .build();

        }

        return noteRoomDatabase;

    }


}
