package ru.olgabelchenko.mytodolist

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.olgabelchenko.mytodolist.data.Note
import ru.olgabelchenko.mytodolist.data.NoteDao

class NotesViewModel(private val noteDao: NoteDao) : ViewModel() {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes().asLiveData()


    /**
     * Returns true if the EditText is not empty.
     */
    fun isEntryValid(noteText: String): Boolean {
        return noteText.isNotBlank()
    }

    /**
     * Inserts the new Note into database.
     */
    fun addNewNote(noteText: String, isChecked: Boolean) {
        val newNote = Note(noteText = noteText, isCrossedOut = isChecked)
        insertNote(newNote)
    }

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insertNote(note)
        }
    }

    /**
     * Edits the existing Note in database.
     */
    fun editNote(id: Int, noteText: String, isChecked: Boolean) {
        val note = Note(id, noteText, isChecked)
        updateNote(note)
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }

    /**
     * Deletes the Note from database
     */
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
    }

    /**
     * Retrieve note from database.
     */
    fun retrieveItem(id: Int): LiveData<Note> {
        return noteDao.getNoteById(id).asLiveData()
    }
}

class NotesViewModelFactory(private val noteDao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}