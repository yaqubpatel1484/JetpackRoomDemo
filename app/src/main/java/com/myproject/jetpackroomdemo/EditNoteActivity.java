package com.myproject.jetpackroomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    private EditText mEdtNote;
    private String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

         Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            noteId = bundle.getString("NOTE_ID");
        }

        mEdtNote = findViewById(R.id.update_edtNote);

        EditNoteViewModel editNoteViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        LiveData<Note> note = editNoteViewModel.getRecord(noteId);
        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                mEdtNote.setText(note.getNote());
            }
        });

    }

    public void onUpdate(View view){

        String updatedNote = mEdtNote.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("NOTE_ID",noteId);
        intent.putExtra("UPDATED_NOTE",updatedNote);
        setResult(RESULT_OK,intent);

        finish();

    }

    public void onCancel(View view){
        finish();
    }

}
