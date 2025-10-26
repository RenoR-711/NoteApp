package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.model.Note

@Dao
interface NoteDao {

    // --- CRUD Operation ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    // Suche: '%' wird vom aufrufenden Code ergänzt, z.B. "%query%"
    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteDesc LIKE :query ORDER BY id DESC")
    fun searchNotes(query: String): LiveData<List<Note>>
}
