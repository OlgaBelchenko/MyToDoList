package ru.olgabelchenko.mytodolist

import android.content.Context.INPUT_METHOD_SERVICE
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
import ru.olgabelchenko.mytodolist.databinding.FragmentAddNoteBinding

/**
 * Fragment for adding new notes
 */
class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory(
            (activity?.application as NoteApplication).database.notesDao()
        )
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

        binding.btnSave.setOnClickListener {
            addNewNote()
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
     * Inserts the new Note into database and navigates up to NotesFragment
     */
    private fun addNewNote() {
        if (isEntryValid()) {
            viewModel.addNewNote(
                this.binding.etText.text.toString(),
                this.binding.cbCrossedOutAdd.isChecked
            )
        }
        val action = AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()
        findNavController().navigate(action)
    }



    /**
     * Before the fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}