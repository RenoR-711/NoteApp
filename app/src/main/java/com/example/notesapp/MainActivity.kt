package com.example.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = NoteDatabase.getDatabase(this).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = factory.create(NoteViewModel::class.java)

        val navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
