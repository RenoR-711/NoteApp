package com.example.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    //region 1   Decl. und Init
    private lateinit var binding: ActivityMainBinding
    //endregion
    lateinit var noteViewModel: NoteViewModel

    //region 2. Lebenszyklus
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Binding Object erstellen (Inflate activity main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //2. Root Elemente festlegen
        //3. Setzen des Hauptelementes
        // setting the content view of your activity or fragment to be the root view
        setContentView(binding.root)
        //instance of ViewModelProvider and obtaining the desired ViewModel using this provider
        setupViewModel()
    }

    private fun setupViewModel(){
        //Initialize Repository with a database instance
        //The "this" keyword refers to the current context
        val noteRepository = NoteRepository(NoteDatabase(this))

        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]

    }//endregion
}
