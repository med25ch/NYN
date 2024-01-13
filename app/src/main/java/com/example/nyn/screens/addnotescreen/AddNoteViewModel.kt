package com.example.nyn.screens.addnotescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
import com.example.nyn.screens.homescreen.NoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepo: OfflineNotesRepository,
    private val categoryRepo : OfflineCategoriesRepository) : ViewModel() {

    val categoriesUiState: StateFlow<CategoriesUiState> =
        categoryRepo.getAllNoteCategoriesStream().map { CategoriesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoriesUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    // MutableState to handle our UI state
    var noteTitle = mutableStateOf("")
    var noteBody = mutableStateOf("")

    suspend fun saveCategoryToDB(categoryName : String){
        val newCategory = NoteCategory(id = 0 , name = categoryName)
        categoryRepo.insertNoteCategory(newCategory)
    }


    suspend fun saveNoteToDB(){

        // Todo : Validate note before saving

        val note = Note(id = 0,
            title = noteTitle.value,
            body = noteBody.value,
            isPinned = false,
            category = "",
            color = "")
        noteRepo.insertNote(note)
    }

    suspend fun deleteCategoryFromDB(noteCategory: NoteCategory){
        categoryRepo.deleteNoteCategory(noteCategory)
    }

    fun updateNoteTitle(title : String){
        noteTitle.value = title
    }

    fun updateNoteBody(body : String){
        noteBody.value = body
    }

}

data class CategoriesUiState(val repoList: List<NoteCategory> = listOf())