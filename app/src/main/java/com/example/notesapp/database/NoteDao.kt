package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {


    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    // 🔍 Suche nach Titel oder Inhalt
    @Query("SELECT * FROM note WHERE noteTitle LIKE :query OR noteDesc LIKE :query ORDER BY id DESC")
    fun searchNotes(query: String): LiveData<List<Note>>
}