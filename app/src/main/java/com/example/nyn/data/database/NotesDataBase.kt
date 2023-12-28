package com.example.nyn.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nyn.data.category.NoteCategory
import com.example.nyn.data.category.NoteCategoryDAO
import com.example.nyn.data.note.Note
import com.example.nyn.data.note.NoteDAO

@Database(entities = [Note::class,NoteCategory::class], version = 1, exportSchema = false)
abstract class NotesDataBase  : RoomDatabase(){

    abstract fun noteDao(): NoteDAO
    abstract fun noteCategoryDAO() :NoteCategoryDAO

    companion object {
        @Volatile
        private var Instance: NotesDataBase? = null

        fun getDatabase(context: Context): NotesDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotesDataBase::class.java, "notes_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}