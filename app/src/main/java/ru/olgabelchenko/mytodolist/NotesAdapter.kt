package ru.olgabelchenko.mytodolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.olgabelchenko.mytodolist.data.Note
import ru.olgabelchenko.mytodolist.databinding.NoteItemBinding

class NotesAdapter(
    private val viewModel: NotesViewModel,
    private val onNoteClicked: (Note) -> Unit
) :
    ListAdapter<Note, NotesAdapter.NoteViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote = getItem(position)

        with(holder) {
            bind(currentNote, position)

            itemView.setOnClickListener {
                onNoteClicked(currentNote)
            }

            binding.ivDelete.setOnClickListener {
                viewModel.deleteNote(currentNote)
            }

            binding.ivEdit.setOnClickListener {
                onNoteClicked(currentNote)
            }

            binding.cbCrossedOut.setOnCheckedChangeListener { button, isChecked ->
                if (isChecked) {
                    viewModel.editNote(currentNote.id, currentNote.noteText, true)
                    binding.tvTodo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    viewModel.editNote(currentNote.id, currentNote.noteText, false)
                    binding.tvTodo.paintFlags = 0
                }
            }
        }


    }

    class NoteViewHolder(var binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, position: Int) {
            with(binding) {
                cbCrossedOut.isChecked = note.isCrossedOut
                tvNumber.text = "${position + 1}."
                tvTodo.text = note.noteText
                if (note.isCrossedOut) {
                    tvTodo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

}