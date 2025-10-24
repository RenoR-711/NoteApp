package com.example.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Datenbank-Instanz korrekt bekommen (Room-Datenbank korrekt holen)
        val db = NoteDatabase.getInstance(this)
        val repository = NoteRepository(db)
        noteViewModel = NoteViewModel(repository)

    }
}
