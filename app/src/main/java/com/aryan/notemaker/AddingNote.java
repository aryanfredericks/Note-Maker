package com.aryan.notemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddingNote extends AppCompatActivity {
    ImageView backBtn,createNote;
    TextInputEditText inputTitle,inputDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_note);

        Intent intent = getIntent();

        backBtn = findViewById(R.id.backBtn);
        createNote = findViewById(R.id.createNote);
        inputTitle = findViewById(R.id.inputTitle);
        inputDesc = findViewById(R.id.inputDesc);

        backBtn.setOnClickListener(v->{
            onBackPressed();
        });

        createNote.setOnClickListener(view->{
            if (inputTitle.getText().toString().isEmpty() || inputDesc.getText().toString().isEmpty()){
                Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            }
            else{
                new BackGroundThread().start();
                Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class BackGroundThread extends Thread{
        @Override
        public void run() {
            super.run();
            NoteDatabase db = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class,"my-database").build();
            NotesDao notesDao = db.dao();
            notesDao.insertNote(new NoteEntity(inputTitle.getText().toString(),inputDesc.getText().toString()));
            inputTitle.setText("");
            inputDesc.setText("");
        }
    }
}