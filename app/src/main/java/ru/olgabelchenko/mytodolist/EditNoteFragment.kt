package ru.olgabelchenko.mytodolist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.olgabelchenko.mytodolist.data.Note
import ru.olgabelchenko.mytodolist.databinding.FragmentEditNoteBinding

/**
 * Fragment for editing and deleting notes
 */
class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory(
            (activity?.application as NoteApplication).database.notesDao()
        )
    }

    private val navArgs: EditNoteFragmentArgs by navArgs()

    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navArgs.noteId

        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            note = selectedItem
            bind(note)
        }

        binding.btnDelete.setOnClickListener {
            deleteNote()
        }

        binding.btnSave.setOnClickListener {
            editNote()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /**
     * Returns true if the EditText is not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(binding.etText.toString())
    }

    /**
     * Binds views with the passed in [note] information
     */
    private fun bind(note: Note) {
        binding.apply {
            etText.setText(note.noteText)
            cbCrossedOutAdd.isChecked = note.isCrossedOut
        }
    }

    /**
     * Deletes the Note from database
     */
    private fun deleteNote() {
        viewModel.deleteNote(note)
        findNavController().navigateUp()
    }

    /**
     * Updates the existing note in the database.
     */
    private fun editNote() {
        if (isEntryValid()) {
            viewModel.editNote(
                this.navArgs.noteId,
                this.binding.etText.text.toString(),
                this.binding.cbCrossedOutAdd.isChecked
            )
        }

        val action = EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment()
        findNavController().navigate(action)
    }

    /**
     * Before the fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}