package com.example.nyn.data.repositories

import com.example.nyn.data.models.note.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [note] from a given data source.
 */
interface NotesRepository {
    /**
     * Retrieve all the notes from the the given data source.
     */
    fun getAllNotesStream(): Flow<List<Note>>

    /**
     * Retrieve an note from the given data source that matches with the [id].
     */
    fun getNoteStream(id: Int): Flow<Note?>

    /**
     * Retrieve an note from the given data source that matches with the category.
     */
    fun getNotesStreamByCategory(category: String): Flow<List<Note>>

    /**
     * Insert note in the data source
     */
    suspend fun insertNote(note: Note)

    /**
     * Delete note from the data source
     */
    suspend fun deleteNote(note: Note)

    /**
     * Update note in the data source
     */
    suspend fun updateNote(note: Note)
}