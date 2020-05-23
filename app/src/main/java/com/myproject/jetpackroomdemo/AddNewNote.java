package com.myproject.jetpackroomdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewNote extends AppCompatActivity {

    private EditText mEdtNote;

    public static String NOTE_ADDED = "NOTE ADDED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        mEdtNote = findViewById(R.id.edtNote);

        Button mBtnAdd = findViewById(R.id.btnAdd);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                if (TextUtils.isEmpty(mEdtNote.getText())){
                    setResult(RESULT_CANCELED,intent);
                }else
                    intent.putExtra(NOTE_ADDED,mEdtNote.getText().toString());
                setResult(RESULT_OK,intent);

                finish();

            }
        });

    }
}
