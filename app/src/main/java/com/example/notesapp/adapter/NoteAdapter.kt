package com.example.notesapp.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNoteBinding
import com.example.notesapp.model.Note

class NoteAdapter(
    private val onItemClick: ((Note) -> Unit)? = null
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.noteTitle.text = highlight(note.noteTitle)
            binding.noteDesc.text = highlight(note.noteDesc)

            binding.root.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // Suchtreffer hervorheben
    private fun highlight(text: String): CharSequence {
        if (currentSearchQuery.isBlank()) return text

        val startIndex = text.lowercase().indexOf(currentSearchQuery.lowercase())
        if (startIndex == -1) return text

        val endIndex = startIndex + currentSearchQuery.length
        val spannable = SpannableString(text)

        spannable.setSpan(
            BackgroundColorSpan(Color.YELLOW),
            startIndex, endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    companion object {
        // für SearchView-Steuerung
        var currentSearchQuery: String = ""
    }
}

// DiffUtil für Listenvergleich
private class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}