package com.example.nyn.data.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class NoteCategory(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String
)
