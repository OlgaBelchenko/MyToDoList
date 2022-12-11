package ru.olgabelchenko.mytodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.olgabelchenko.mytodolist.databinding.FragmentAddNoteBinding
import ru.olgabelchenko.mytodolist.databinding.FragmentNotesBinding

class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) {}

    }
}