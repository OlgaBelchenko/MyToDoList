package ru.olgabelchenko.mytodolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.olgabelchenko.mytodolist.data.Note
import ru.olgabelchenko.mytodolist.data.NoteDao
import ru.olgabelchenko.mytodolist.databinding.NoteItemBinding

class NotesAdapter : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, position)
    }

    class NoteViewHolder(private var binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(note: Note, position: Int) {
                binding.cbCrossedOut.isChecked = note.isCrossedOut
                binding.tvNumber.text = "${position + 1}."
                binding.tvTodo.text = note.noteText
                binding.ivEdit.setOnClickListener {  }
                binding.ivDelete.setOnClickListener {  }
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