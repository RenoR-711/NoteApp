package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView = view

        // MenuHost einrichten, damit Fragment Menüs bereitstellen kann
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // ViewModel vom MainActivity holen
        notesViewModel = (activity as MainActivity).noteViewModel
    }

    private fun saveNote() {
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteContent = binding.addNoteContent.text.toString().trim()

        if (noteTitle.isEmpty() || noteContent.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(
            title = noteTitle,
            content = noteContent
        )

        notesViewModel.insertNote(note)
        Toast.makeText(requireContext(), "Note saved!", Toast.LENGTH_SHORT).show()

        // Zurück zum NotesFragment navigieren
        rootView.findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() { // setzt _bindung auf null zur Vermeidung von Memory Leaks
        super.onDestroyView()
        _binding = null
    }
}
