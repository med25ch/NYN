package com.example.nyn.data.repositories

import com.example.nyn.data.category.NoteCategory
import com.example.nyn.data.category.NoteCategoryDAO
import kotlinx.coroutines.flow.Flow

class OfflineCategoriesRepository(private val noteCategoryDAO: NoteCategoryDAO) : CategoriesRepository {
    override fun getAllNoteCategoriesStream(): Flow<List<NoteCategory>> {
        return noteCategoryDAO.getAllNotesCategories()
    }

    override fun getNoteCategoryStream(id: Int): Flow<NoteCategory?> {
        return noteCategoryDAO.getNoteCategory(id)
    }

    override suspend fun insertNoteCategory(noteCategory: NoteCategory) {
        noteCategoryDAO.insert(noteCategory)
    }

    override suspend fun deleteNoteCategory(noteCategory: NoteCategory) {
        noteCategoryDAO.delete(noteCategory)
    }

    override suspend fun updateNoteCategory(noteCategory: NoteCategory) {
        noteCategoryDAO.update(noteCategory)
    }
}