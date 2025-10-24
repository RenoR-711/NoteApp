package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note
import com.example.notesapp.model.NoteDatabase
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel-Schicht:
 * Vermittelt zwischen UI (Activity/Fragment) und Repository.
 * Führt Datenoperationen in Coroutines aus (asynchron, thread-sicher).
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    // --- Schreiboperationen (CRUD) ---
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun searchNote(query: String): LiveData<List<Note>> {
        return repository.searchNote(query)
    }
}

