package ru.olgabelchenko.mytodolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "text")
    val noteText: String,
    @ColumnInfo(name = "is_checked")
    val isCrossedOut: Boolean = false
)