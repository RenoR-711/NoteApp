package com.example.notesapp.database

import androidx.room.*
import com.example.notesapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE title LIKE :query OR content LIKE :query")
    fun searchNotes(query: String?): Flow<List<Note>>
}
