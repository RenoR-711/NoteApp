package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel


class UpdateNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        // Menü einrichten
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Note aus den Safe Args laden
        currentNote = args.note
        binding.addNoteTitle.setText(currentNote.noteTitle)
        binding.addNoteDesc.setText(currentNote.noteDesc)
    }

    private fun updateNote() {
        val updatedTitle = binding.addNoteTitle.text.toString().trim()
        val updatedDesc = binding.addNoteDesc.text.toString().trim()

        if (updatedTitle.isEmpty() || updatedDesc.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedNote = currentNote.copy(
                noteTitle = updatedTitle,
                noteDesc = updatedDesc
        )

        viewLifecycleOwner.lifecycleScope.launch {
            noteViewModel.update(updatedNote)
            Toast.makeText(requireContext(), "Note updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
        }
    }

    // Menü erstellen
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_add_note, menu) // kann gleiche Menü-Datei wie AddNoteFragment sein
    }

    // Menü-Item klicken
    override fun onMenuItemSelected(menuItem:MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                updateNote()
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
