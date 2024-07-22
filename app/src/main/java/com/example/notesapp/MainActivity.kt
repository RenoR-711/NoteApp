package com.example.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

/**
 * Main activity
 * Einstiegspunkt in die App
 * @constructor Create empty Main activity
 */
class MainActivity : AppCompatActivity() {
    //region 1   Decl. und Init
    private lateinit var binding: ActivityMainBinding
    //endregion
    lateinit var noteViewModel: NoteViewModel

    //region 2. Lebenszyklus

    /**
     * Startet als erstes nach dem Konstruktor
     * Setzt das Layout und generiert alle direkt
     * benoetigten Widgets. Setzt Listener.
     * Die Activity ist zur diesem Zeitpunkt noch nicht sichtbar
     * @param savedInstanceState [Bundle] - Zwischenspeicherungsobject
     */
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
    /**
     * Setup view model: enthält die Geschäftslogik
     * ->  ist für die Verwaltung und Speicherung der Daten für eine Aktivität oder
     *      ein Fragment zuständig
     */
    private fun setupViewModel(){
        //Initialize Repository with a database instance
        //The "this" keyword refers to the current context
        val noteRepository = NoteRepository(NoteDatabase(this))

        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]

        /**This code snippet is creating a NoteViewModelFactory and using it to create a
         * NoteViewModel instance. The NoteViewModelFactory takes the application context and
         * noteRepository as parameters, and then it is used to instantiate the NoteViewModel using
         * ViewModelProvider.
         * The ViewModelProvider takes this (which refers to the current activity or fragment)
         * as the first parameter, and viewModelProviderFactory as the second parameter. It then
         * retrieves an instance of NoteViewModel by passing NoteViewModel::class.java as a parameter.
         * Overall, this code sets up the necessary components for creating an instance of
         * NoteViewModel with proper dependencies injected.
         */

    }//endregion
}