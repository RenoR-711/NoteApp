package com.example.notesapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel-Initialisierung mit Factory (modern & lifecycle-aware)
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            application,
            NoteRepository(NoteDatabase(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
    }

    /**
     * Beobachtet Änderungen in den Notizen und aktualisiert die UI.
     * (RecyclerView, Adapter etc. – hier als Platzhalter)
     */
    private fun setupObservers() {
        noteViewModel.getAllNotes().observe(this) { notes ->
            // TODO: RecyclerView oder ListAdapter aktualisieren
            // exampleAdapter.submitList(notes)
        }
    }
}
