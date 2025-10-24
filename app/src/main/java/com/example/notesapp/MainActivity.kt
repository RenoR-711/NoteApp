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

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // optional: BottomNavigationView oder Toolbar einbinden
    }
}
