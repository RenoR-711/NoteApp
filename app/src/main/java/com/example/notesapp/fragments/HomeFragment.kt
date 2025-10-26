package com.example.notesapp.fragments

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    private val deleteBackground = ColorDrawable(Color.LTGRAY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // Shared ViewModel
        noteViewModel = (requireActivity() as MainActivity).noteViewModel

        // RecyclerView setup + Navigation on Click
        noteAdapter = NoteAdapter { note ->
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(note)
            findNavController().navigate(action)
        }
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRecyclerView.adapter = noteAdapter

        // Swipe Delete + Snackbar Undo
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteAdapter.currentList[position]
                noteViewModel.delete(note)

                Snackbar.make(binding.root, "Notiz gelöscht", Snackbar.LENGTH_LONG)
                    .setAction("Rückgängig") { noteViewModel.insert(note) }
                    .show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                deleteBackground.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
                deleteBackground.draw(c)

                val deleteIcon = context?.getDrawable(R.drawable.outline_delete_forever_24)
                deleteIcon?.let {
                    val iconMargin = (itemView.height - it.intrinsicHeight) / 2
                    val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
                    val iconLeft = if (dX > 0) {
                        itemView.left + iconMargin
                    } else {
                        itemView.right - iconMargin - it.intrinsicWidth
                    }
                    val iconRight = iconLeft + it.intrinsicWidth
                    val iconBottom = iconTop + it.intrinsicHeight
                    it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    it.draw(c)
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.homeRecyclerView)

        // Observe Notes + Empty State
        noteViewModel.allNotes.observe(viewLifecycleOwner) { list ->
            NoteAdapter.currentSearchQuery = "" // reset highlight
            noteAdapter.submitList(list)

            val isEmpty = list.isNullOrEmpty()
            binding.homeRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
            binding.emptyNotesImage.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.emptyNotesText.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }

        // Add Note Navigation
        binding.addNoteFab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

        // MenuProvider (moderne Suchfunktion)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)

                val searchItem = menu.findItem(R.id.searchMenu)
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = "Notizen durchsuchen..."

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?) = true

                    override fun onQueryTextChange(newText: String?): Boolean {
                        NoteAdapter.currentSearchQuery = newText ?: ""
                        noteViewModel.searchNotes(newText ?: "")
                            .observe(viewLifecycleOwner) { filteredList ->
                                noteAdapter.submitList(filteredList)
                            }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem) = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
