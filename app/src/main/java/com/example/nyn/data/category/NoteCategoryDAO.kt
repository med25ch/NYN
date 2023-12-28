package com.example.nyn.data.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nyn.data.note.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteCategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteCategory: NoteCategory)

    @Update
    suspend fun update(noteCategory: NoteCategory)

    @Delete
    suspend fun delete(noteCategory: NoteCategory)

    @Query("SELECT * from category_table WHERE id = :id")
    fun getNoteCategory(id: Int): Flow<NoteCategory>

    @Query("SELECT * from category_table ORDER BY id")
    fun getAllNotesCategories(): Flow<List<NoteCategory>>

}