package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.Note

// Room-Datenbank für Notizen
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    // DAO bereitstellen
    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        private val LOCK = Any() // Lock-Objekt für Thread-Sicherheit

        // Singleton-Zugriff auf die Datenbank
        fun getInstance(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: createDatabase(context).also { INSTANCE = it }
            }

        // Datenbank erstellen
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            )
                .fallbackToDestructiveMigration(false) // Falls Schema sich ändert
                .build()
    }
}
