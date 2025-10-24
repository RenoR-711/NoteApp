package com.example.notesapp.model

import androidx.lifecycle.LiveData
import com.example.notesapp.database.NoteDao

/**
 * Repository-Schicht:
 * Vermittelt zwischen ViewModel und Room-Datenbank.
 * Kapselt Datenzugriffe und sorgt für klare Trennung von UI-Logik.
 */
class NoteRepository(private val dao: NoteDao) {

    val allNotes: LiveData<List<Note>> = dao.getAllNotes()

    // --- CRUD ---
    suspend fun insert(note: Note) = dao.insert(note)
    suspend fun update(note: Note) = dao.update(note)
    suspend fun delete(note: Note) = dao.delete(note)

    // --- Leseoperationen, Queries --
    fun searchNote(query: String): LiveData<List<Note>> = dao.searchNote(query)
}

