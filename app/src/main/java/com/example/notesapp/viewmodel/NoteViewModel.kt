package com.example.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel-Schicht: Vermittelt zwischen UI (Fragment/Activity) und Repository.
 * Führt alle Datenoperationen asynchron mit Coroutines aus.
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // Alle Notizen als LiveData
    val allNotes: LiveData<List<Note>> = repository.allNotes

    // --- CRUD Operationen ---
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    // Suche nach Notizen
    fun searchNote(query: String): LiveData<List<Note>> {
        return repository.searchNote(query)
    }
}
