package com.example.nyn.data.note

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nyn.data.category.NoteCategory

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title : String,
    val body : String,
    val isPinned : Boolean,
    val category: NoteCategory,
    val color : Color,
    /*val user : User?*/)