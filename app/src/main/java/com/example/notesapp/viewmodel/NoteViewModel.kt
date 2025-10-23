package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

/**
 * ViewModel-Schicht:
 * Vermittelt zwischen UI (Activity/Fragment) und Repository.
 * Führt Datenoperationen in Coroutines aus (asynchron, thread-sicher).
 */
class NoteViewModel(
    app: Application,
    private val repository: NoteRepository
) : AndroidViewModel(app) {

    // --- Schreiboperationen (CRUD) ---

    fun addNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    // --- Leseoperationen ---

    fun getAllNotes() = repository.getAllNotes()

    fun searchNotes(query: String?) = repository.searchNotes(query)
}
