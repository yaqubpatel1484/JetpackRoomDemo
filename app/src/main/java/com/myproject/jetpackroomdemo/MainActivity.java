package com.myproject.jetpackroomdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnDeleteClickListner{

    private Context mContext;
    private static final int ADD_NOTE_REQUEST_CODE = 1 ;
    public static final int UPDATE_NOTE_REQUEST_CODE = 101;
    private MainActivityViewModel mainActivityViewModel;
    private NotesAdapter notesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddNewNote.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST_CODE);

            }
        });

        mContext = this;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        notesAdapter = new NotesAdapter(mContext,this);
        recyclerView.setAdapter(notesAdapter);

         mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        LiveData<List<Note>> mNotesLiveData = mainActivityViewModel.getAllNotes();
        mNotesLiveData.observe(this, new Observer<List<Note>>() {
             @Override
             public void onChanged(List<Note> notes) {

                 ArrayList<Note> notesList = new ArrayList<>(notes);

                 Note note = notes.get(2);
                 Log.e("Data-", note.getNote());

                 notesAdapter.setData(notesList);
             }
         });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK){

            String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id,data.getStringExtra(AddNewNote.NOTE_ADDED));
            mainActivityViewModel.insert(note);

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            
        } else if(requestCode == UPDATE_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {

            String noteId = data.getStringExtra("NOTE_ID");
            String updatedNote = data.getStringExtra("UPDATED_NOTE");
            Note newNote = new Note(noteId,updatedNote);
            mainActivityViewModel.updateRecord(newNote);

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDelete(Note note) {
            mainActivityViewModel.deleteRecord(note);
        Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
    }
}
