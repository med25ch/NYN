package com.example.nyn.data.models.category

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class NoteCategory(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
)
