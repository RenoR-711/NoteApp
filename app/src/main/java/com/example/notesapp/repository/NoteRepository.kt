package com.example.notesapp.repository

import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note
import kotlinx.coroutines.flow.Flow


/**
 * Repository-Schicht:
 * Vermittelt zwischen ViewModel und Room-Datenbank.
 * Kapselt Datenzugriffe und sorgt für klare Trennung von UI-Logik.
 */
class NoteRepository(private val db: NoteDatabase) {

    fun getAllNotes() = db.getNoteDao().getAllNotes()

    // --- CRUD ---
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)

    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    // --- Leseoperationen, Queries --
    fun searchNotes(query: String) = db.getNoteDao().searchNotes(query)
}
