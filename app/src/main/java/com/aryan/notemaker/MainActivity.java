package com.aryan.notemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView add_note,reloadNotes;
    RecyclerView recyclerView;


    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_note=findViewById(R.id.add_note);
        reloadNotes=findViewById(R.id.reloadNotes);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadNotes();
        add_note.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this , AddingNote.class);
            MainActivity.this.startActivity(i);
        });
        reloadNotes.setOnClickListener(jkdb->{
            Toast.makeText(this, "Reloading Notes", Toast.LENGTH_SHORT).show();
            loadNotes();
        });
    }

    public void loadNotes(){
        NoteDatabase db = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class,"my-database").allowMainThreadQueries().build();
        NotesDao notesDao = db.dao();
        ArrayList<NoteEntity> arrayList = (ArrayList<NoteEntity>) notesDao.getAllNotes();
        NoteRecyclerAdapter adapter = new NoteRecyclerAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
    }
}