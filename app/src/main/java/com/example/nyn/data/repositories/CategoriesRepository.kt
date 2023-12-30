package com.example.nyn.data.repositories

import com.example.nyn.data.models.category.NoteCategory
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [noteCategory] from a given data source.
 */
interface CategoriesRepository {
    /**
     * Retrieve all the noteCategories from the the given data source.
     */
    fun getAllNoteCategoriesStream(): Flow<List<NoteCategory>>

    /**
     * Retrieve an noteCategory from the given data source that matches with the [id].
     */
    fun getNoteCategoryStream(id: Int): Flow<NoteCategory?>

    /**
     * Insert noteCategory in the data source
     */
    suspend fun insertNoteCategory(noteCategory: NoteCategory)

    /**
     * Delete noteCategory from the data source
     */
    suspend fun deleteNoteCategory(noteCategory: NoteCategory)

    /**
     * Update noteCategory in the data source
     */
    suspend fun updateNoteCategory(noteCategory: NoteCategory)
}