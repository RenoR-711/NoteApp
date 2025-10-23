package com.example.notesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,         // Standardwert für Room (autoGenerate)
    val noteTitle: String,
    val noteDesc: String
) : Parcelable

