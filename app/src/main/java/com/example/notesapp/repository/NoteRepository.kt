package com.example.notesapp.repository

import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note


/**
 * Repository-Schicht:
 * Vermittelt zwischen ViewModel und Room-Datenbank.
 * Kapselt Datenzugriffe und sorgt für klare Trennung von UI-Logik.
 */
class NoteRepository(db: NoteDatabase) {

    private val noteDao = db.getNoteDao()

    // --- CRUD ---
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    // --- Leseoperationen ---
    fun getAllNotes() = noteDao.getAllNotes()
    fun searchNotes(query: String?) = noteDao.searchNotes(query)
}
