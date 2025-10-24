package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentUpdateNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private val args: UpdateNoteFragmentArgs by navArgs() // <- Safe Args für die Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel holen
        notesViewModel = (activity as MainActivity).noteViewModel

        // Alte Notizdaten anzeigen
        val note = args.note
        binding.updateNoteTitle.setText(note.noteTitle)
        binding.updateNoteDesc.setText(note.noteDesc)

        // Update-Button
        binding.updateNoteFab.setOnClickListener {
            val title = binding.updateNoteTitle.text.toString().trim()
            val desc = binding.updateNoteDesc.text.toString().trim()

            if (title.isNotEmpty()) {
                val updatedNote = Note(note.id, title, desc)
                notesViewModel.update(updatedNote)

                Snackbar.make(view, "Note updated successfully", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            } else {
                Snackbar.make(view, "Title cannot be empty", Snackbar.LENGTH_SHORT).show()
            }
        }

        // Delete-Button
        binding.deleteNoteFab.setOnClickListener {
            notesViewModel.delete(note)
            Snackbar.make(view, "Note deleted", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
