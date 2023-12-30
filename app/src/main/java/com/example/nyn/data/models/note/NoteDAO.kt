package com.example.nyn.data.models.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.data.models.note.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from notes_table WHERE id = :id")
    fun getNote(id: Int): Flow<Note>

    @Query("SELECT * from notes_table ORDER BY id")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * from notes_table WHERE category = :category")
    fun getNotesByCategory(category: String): Flow<List<Note>>
}