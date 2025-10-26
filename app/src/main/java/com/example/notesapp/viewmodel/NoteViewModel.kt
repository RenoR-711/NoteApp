package com.example.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // Alle Notizen als LiveData
    val allNotes: LiveData<List<Note>> = repository.allNotes

    // --- CRUD Operationen ---
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insert(note)
        } catch (e: Exception) {
            // Optional: Log.e("NoteViewModel", "insert failed", e)
        }
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.update(note)
        } catch (e: Exception) {
            // Optional: Log.e("NoteViewModel", "update failed", e)
        }
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.delete(note)
        } catch (e: Exception) {
            // Optional: Log.e("NoteViewModel", "delete failed", e)
        }
    }

    // Suche nach Notizen
    fun searchNotes(query: String): LiveData<List<Note>> {
        return repository.searchNotes(query)
    }
}
