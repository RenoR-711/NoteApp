package com.example.notesapp.database

import androidx.room.*
import com.example.notesapp.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Note>

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}
