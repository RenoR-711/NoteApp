package com.example.notesapp.model

import androidx.lifecycle.LiveData
import com.example.notesapp.database.NoteDao

/**
 * Repository-Schicht:
 * Vermittelt zwischen ViewModel und Room-Datenbank.
 * Kapselt Datenzugriffe und sorgt für klare Trennung von UI-Logik.
 */
class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    // --- CRUD ---
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    // --- Leseoperationen, Queries --
    fun searchNotes(query: String): LiveData<List<Note>> {
        val q = "%$query%"
        return noteDao.searchNotes(q)
    }
}