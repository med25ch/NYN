package com.example.nyn.data.models.note

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nyn.data.models.category.NoteCategory

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var title : String,
    var body : String,
    var isPinned : Boolean,
    var category: String,
    var color : Long,
    /*val user : User?*/)