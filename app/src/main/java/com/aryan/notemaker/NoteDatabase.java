package com.aryan.notemaker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteEntity.class} , version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NotesDao dao();
}
