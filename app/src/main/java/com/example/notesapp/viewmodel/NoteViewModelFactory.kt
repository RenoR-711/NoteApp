package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repository.NoteRepository

/**
 * Factory für NoteViewModel:
 * Erstellt eine Instanz von NoteViewModel mit den erforderlichen Abhängigkeiten.
 */
class NoteViewModelFactory(
    private val app: Application,
    private val repository: NoteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Prüfen, ob das ViewModel die richtige Klasse ist
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
