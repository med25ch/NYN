package com.example.nyn.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.data.models.category.NoteCategoryDAO
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.models.note.NoteDAO

@Database(entities = [Note::class, NoteCategory::class], version = 1, exportSchema = false)
abstract class NotesDataBase  : RoomDatabase(){

    abstract fun noteDao(): NoteDAO
    abstract fun noteCategoryDAO() : NoteCategoryDAO
}