package com.myproject.jetpackroomdemo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
// If we don't pass table name here ROom will takes class name as table name by default
public class Note {


    @PrimaryKey
    @NonNull
    private String note_id;

    @NonNull
    @ColumnInfo(name = "notes")
    private String note;


    public Note(@NonNull String note_id, @NonNull String note) {
        this.note_id = note_id;
        this.note = note;
    }

    @NonNull
    public String getNote_id() {
        return note_id;
    }

    @NonNull
    public String getNote() {
        return note;
    }
}
