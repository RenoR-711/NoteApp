package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteLayoutBinding
import com.example.notesapp.fragments.HomeFragmentDirections
import com.example.notesapp.model.Note

class NoteAdapter(
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val notes = List<Note>()

    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.titleText.text = note.noteTitle
            binding.descText.text = note.noteDesc

            binding.root.setOnClickListener {
                onItemClick(note) // klick Event weitergeben
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.binding.noteTitle.text = currentNote.noteTitle
        holder.binding.noteDesc.text = currentNote.noteDesc
    }

    override fun getItemCount() = notes.size

    fun setNotes(list: List<Note>) {
        notes.clear()
        notes.addAll(list)
        notifyDataSetChanged()
    }
}

annotation class ItemNoteBinding
