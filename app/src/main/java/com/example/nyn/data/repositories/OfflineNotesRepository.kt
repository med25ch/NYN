package com.example.nyn.data.repositories

import com.example.nyn.data.note.Note
import com.example.nyn.data.note.NoteDAO
import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val noteDAO: NoteDAO) : NotesRepository{
    override fun getAllNotesStream(): Flow<List<Note>> {
        return noteDAO.getAllNotes()
    }

    override fun getNoteStream(id: Int): Flow<Note?> {
        return noteDAO.getNote(id)
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