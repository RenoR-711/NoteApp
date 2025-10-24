package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel-Schicht:
 * Vermittelt zwischen UI (Activity/Fragment) und Repository.
 * Führt Datenoperationen in Coroutines aus (asynchron, thread-sicher).
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    fun getAllNotes() = repository.getAllNotes()

    // --- Schreiboperationen (CRUD) ---
    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun searchNote(query: String) = repository.searchNotes(query)
}
