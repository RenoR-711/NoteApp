package com.example.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    private lateinit var navController: NavController // NavController als Klassenvariable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- ViewModel initialisieren ---
        val dao = NoteDatabase.getDatabase(this).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        // --- Navigation ActionBar einrichten ---
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
