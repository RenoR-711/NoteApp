package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentUpdateNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private val args: UpdateNoteFragmentArgs by navArgs() // Safe Args

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = (activity as MainActivity).noteViewModel

        // Alte Note anzeigen
        val note = args.note
        binding.updateNoteTitle.setText(note.noteTitle)
        binding.updateNoteDesc.setText(note.noteDesc)

        // Save-Button
        binding.updateNoteFab.setOnClickListener {
            updateNote(note)
        }
    }

    private fun updateNote(note: Note) {
        val title = binding.updateNoteTitle.text.toString().trim()
        val desc = binding.updateNoteDesc.text.toString().trim()

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedNote = note.copy(noteTitle = title, noteDesc = desc)

        lifecycleScope.launch {
            notesViewModel.update(updatedNote)
            Toast.makeText(requireContext(), "Note updated!", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
