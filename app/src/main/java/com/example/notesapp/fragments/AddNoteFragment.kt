package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    // Activity-scoped ViewModel: shared with other fragments
    private val noteViewModel: NoteViewModel by activityViewModels {
        val dao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(dao)
        NoteViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MenuHost einrichten, damit Fragment Menüs bereitstellen kann
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun saveNote() {
        val title = binding.addNoteTitle.text?.toString()?.trim().orEmpty()
        val desc = binding.addNoteDesc.text?.toString()?.trim().orEmpty()

        if (title.isBlank() || desc.isBlank()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(
            noteTitle = title,
            noteDesc = desc
        )
        // suspend-safe Aufruf in Coroutine
        noteViewModel.insert(note)
        Toast.makeText(requireContext(), "Note saved!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addNoteFragment_to_homeFragment)
    }

    // Menü erstellen
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    // Menü-Item klicken
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
