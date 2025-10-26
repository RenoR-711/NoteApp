package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.viewmodel.NoteViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // Shared ViewModel
        noteViewModel = (requireActivity() as MainActivity).noteViewModel

        // RecyclerView Setup
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRecyclerView.adapter = noteAdapter

        // Swipe-to-delete + Snackbar undo
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteAdapter.currentList[position]

                // Delete immediately
                noteViewModel.delete(note)

                // Undo option
                Snackbar.make(binding.root, "Notiz gelöscht", Snackbar.LENGTH_LONG)
                    .setAction("Rückgängig") {
                        noteViewModel.insert(note)
                    }.show()
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.homeRecyclerView)

        // Observe changes
        noteViewModel.allNotes.observe(viewLifecycleOwner) { list ->
            noteAdapter.submitList(list)

            if (list.isNullOrEmpty()) {
                binding.homeRecyclerView.visibility = View.GONE
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.emptyNotesText.visibility = View.VISIBLE
            } else {
                binding.homeRecyclerView.visibility = View.VISIBLE
                binding.emptyNotesImage.visibility = View.GONE
                binding.emptyNotesText.visibility = View.GONE
            }
        }
            // FAB -> AddNoteFragment
            binding.addNoteFab.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
            }
    }
}
