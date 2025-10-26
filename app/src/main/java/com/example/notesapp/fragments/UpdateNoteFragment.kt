package com.example.notesapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.notesapp.R
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.FragmentUpdateNoteBinding
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpdateNoteBinding.bind(view)

        // Get shared ViewModel from Activity-scope
        noteViewModel = (requireActivity() as MainActivity).noteViewModel

        // Show existing note
        val note = args.note
        binding.updateNoteTitle.setText(note.noteTitle)
        binding.updateNoteDesc.setText(note.noteDesc)

        // Update Button
        binding.updateNoteFab.setOnClickListener {
            val updatedNote = note.copy(
                noteTitle = binding.updateNoteTitle.text.toString(),
                noteDesc = binding.updateNoteDesc.text.toString()
            )
            noteViewModel.update(updatedNote)
            requireView().findNavController()
                .navigate(R.id.action_updateNoteFragment_to_homeFragment)
        }

        // Delete Button
        binding.deleteNoteFab.setOnClickListener {
            noteViewModel.delete(note)
            requireView().findNavController()
                .navigate(R.id.action_updateNoteFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}