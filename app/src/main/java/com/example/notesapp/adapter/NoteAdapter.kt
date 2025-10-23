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

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            // Nur die ID reicht aus, um festzustellen ob es das gleiche Item ist
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            // Inhaltliche Gleichheit prüfen
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differ.currentList[position]

        holder.binding.noteTitle.text = note.noteTitle
        holder.binding.noteDesc.text = note.noteDesc

        holder.itemView.setOnClickListener {
            // Navigation mit SafeArgs: Übergabe des Note-Objekts
            val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(note)
            it.findNavController().navigate(direction)
        }
    }
}
