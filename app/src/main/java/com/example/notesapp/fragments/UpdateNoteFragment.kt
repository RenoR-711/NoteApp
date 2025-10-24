package com.example.notesapp.fragments

import android.widget.Toast

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs

import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentUpdateNoteBinding

import kotlinx.coroutines.launch

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private val args: UpdateNoteFragmentArgs by navArgs()  // Übergabe von Note

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

        // Daten aus Argumenten setzen, Note aus den Safe Args laden
        binding.updateNoteTitle.setText(args.note.noteTitle)
        binding.updateNoteDesc.setText(args.note.noteDesc)

        binding.updateNoteFab.setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote() {
        val title = binding.updateNoteTitle.text.toString().trim()
        val desc = binding.updateNoteDesc.text.toString().trim()

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedNote = args.note.copy(noteTitle = title, noteDesc = desc)

        lifecycleScope.launch {
            notesViewModel.update(updatedNote)
            Toast.makeText(requireContext(), "Note updated!", Toast.LENGTH_SHORT).show()
            binding.root.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
