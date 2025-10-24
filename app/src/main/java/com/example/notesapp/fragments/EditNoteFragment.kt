package com.example.notesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menü einrichten
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // ViewModel vom Activity holen
        notesViewModel = (activity as MainActivity).noteViewModel

        // Note aus den Argumenten holen
        currentNote = args.note!! //<-- sicher non-null

        // Felder mit existierenden Werten füllen
        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        // Speichern-Button
        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if (noteTitle.isNotEmpty()) {
                val updatedNote = Note(currentNote.id, noteTitle, noteDesc)
                notesViewModel.update(updatedNote)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(requireContext(), "Bitte geben Sie einen Titel ein", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireContext())
            .setTitle("Notiz löschen")
            .setMessage("Wollen Sie diese Notiz wirklich löschen?")
            .setPositiveButton("Löschen") { _, _ ->
                notesViewModel.delete(currentNote)
                Toast.makeText(requireContext(), "Notiz gelöscht", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            .setNegativeButton("Abbrechen", null)
            .create()
            .show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.deleteMenu -> {
            deleteNote()
            true
        }
        else -> false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
