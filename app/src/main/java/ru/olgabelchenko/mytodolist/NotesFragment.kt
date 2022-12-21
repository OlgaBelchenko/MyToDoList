package ru.olgabelchenko.mytodolist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.olgabelchenko.mytodolist.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory(
            (activity?.application as NoteApplication).database.notesDao()
        )
    }
    private lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFloating.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }
    }

}