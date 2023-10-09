package com.aryan.notemaker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao{

    @Query("select * from notes")
    List<NoteEntity> getAllNotes();

    @Insert
    void insertNote(NoteEntity note);

    @Query("delete from notes where id = :id")
    void deleteNote(int id);

    @Update
    void updateNote(NoteEntity note);

}
