package com.example.nyn.data.repositories

import com.example.nyn.data.models.note.Note
import com.example.nyn.data.models.note.NoteDAO
import dagger.Module
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineNotesRepository @Inject constructor (private val noteDAO: NoteDAO) : NotesRepository{
    override fun getAllNotesStream(): Flow<List<Note>> {
        return noteDAO.getAllNotes()
    }

    override fun getNoteStream(id: Int): Flow<Note?> {
        return noteDAO.getNote(id)
    }

    override fun getNotesStreamByCategory(category: String): Flow<List<Note>> {
        return noteDAO.getNotesByCategory(category)
    }

    override suspend fun insertNote(note: Note) {
        noteDAO.insert(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDAO.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDAO.update(note)
    }
}
