package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home),
    androidx.appcompat.widget.SearchView.OnQueryTextListener, MenuProvider {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// Menü einrichten
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // --- ViewModel initialisieren ---
        val dao = NoteDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        // --- RecyclerView einrichten ---
        setupRecyclerView()

        // --- FloatingActionButton für neue Notiz ---
        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

        // --- Observer ---
        observeNotes()
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter().apply {
            onItemClick = { selectedNote ->
                // SafeArgs Navigation zur UpdateNoteFragment
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(selectedNote)
                binding.root.findNavController().navigate(action)
            }
        }

        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
    }

    private fun observeNotes() {
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            noteAdapter.differ.submitList(notes)
            updateUI(notes)
        }
    }

    private fun updateUI(notes: List<com.example.notesapp.model.Note>) {
        if (notes.isNotEmpty()) {
            binding.emptyNotesImage.visibility = View.GONE
            binding.homeRecyclerView.visibility = View.VISIBLE
        } else {
            binding.emptyNotesImage.visibility = View.VISIBLE
            binding.homeRecyclerView.visibility = View.GONE
        }
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%${query ?: ""}%"
        noteViewModel.searchNote(searchQuery).observe(viewLifecycleOwner) { list ->
            noteAdapter.differ.submitList(list)
            updateUI(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        searchNote(newText)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as androidx.appcompat.widget.SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
}
