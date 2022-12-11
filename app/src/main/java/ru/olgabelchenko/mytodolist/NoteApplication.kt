package ru.olgabelchenko.mytodolist

import android.app.Application
import ru.olgabelchenko.mytodolist.data.NoteDatabase

class NoteApplication : Application() {

    val database: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
}