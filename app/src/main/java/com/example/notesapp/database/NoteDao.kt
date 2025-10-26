package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    // --- CRUD Operation ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    // Suche: '%' wird vom aufrufenden Code ergänzt, z.B. "%query%"
    @Query("SELECT * FROM notes_table WHERE noteTitle LIKE :query OR noteDesc LIKE :query ORDER BY id DESC")
    fun searchNotes(query: String): LiveData<List<Note>>
}
