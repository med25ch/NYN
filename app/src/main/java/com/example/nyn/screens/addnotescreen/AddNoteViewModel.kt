package com.example.nyn.screens.addnotescreen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
import com.example.nyn.screens.homescreen.HomeScreenViewModel
import com.example.nyn.screens.homescreen.NoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepo: OfflineNotesRepository,
    private val categoryRepo : OfflineCategoriesRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val DEFAULT_CATEGORY_INDEX = -1
    }


    // MutableState to handle our UI state
    var noteTitle = mutableStateOf("")
    var noteBody = mutableStateOf("")
    var selectedCategory = mutableStateOf("")
    private var isPinned : Boolean = false
    var isAnUpdate : Boolean = false
    private var noteIdToUpdate : Int = -10


    val updatedNoteUiState: StateFlow<Any> =
        noteRepo.getNoteStream(noteIdToUpdate).map {
            if (it != null) {
                UpdatedNoteUiState(it)
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UpdatedNoteUiState()
            )

    val categoriesUiState: StateFlow<CategoriesUiState> =
        categoryRepo.getAllNoteCategoriesStream().map { CategoriesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoriesUiState()
            )

    suspend fun saveCategoryToDB(categoryName : String){
        val newCategory = NoteCategory(id = 0 , name = categoryName)
        categoryRepo.insertNoteCategory(newCategory)
    }

    suspend fun saveNoteToDB(noteCategory: String = ""){

        // Todo : Validate note before saving
        if (isAnUpdate){
            updateNote()
        }else{
            val note = Note(id = 0,
                title = noteTitle.value,
                body = noteBody.value,
                isPinned = isPinned,
                category = getSelectedCategoryName(),
                color = "")
            noteRepo.insertNote(note)
        }
    }

    private suspend fun updateNote(){

        val noteToUpdate = noteRepo.getNoteStream(noteIdToUpdate).first()

        noteToUpdate?.body = noteBody.value
        noteToUpdate?.title = noteTitle.value
        noteToUpdate?.isPinned = isPinned
        noteToUpdate?.category = getSelectedCategoryName()

        if (noteToUpdate != null) {
            noteRepo.updateNote(noteToUpdate)
        }
    }

    private fun getSelectedCategoryName(): String {
            return selectedCategory.value
    }

    fun setSelectedCategoryName(categoryName: String) {
        selectedCategory.value = categoryName
    }

    suspend fun deleteCategoryFromDB(noteCategory: NoteCategory, resetSelectedCategory: Boolean){
        if(resetSelectedCategory){
            selectedCategory.value = ""
        }
        categoryRepo.deleteNoteCategory(noteCategory)
    }

    fun updateNoteTitle(title : String){
        noteTitle.value = title
    }

    fun updateNoteBody(body : String){
        noteBody.value = body
    }

    fun isPinnedNote(isPinned : Boolean = false) : Boolean{
        this.isPinned = isPinned
        return this.isPinned
    }

    fun updateNoteMode(update : Boolean){
        isAnUpdate = update
    }

    fun setNoteIdToUpdate(noteId: Int) {
        noteIdToUpdate = noteId
    }
}

data class CategoriesUiState(val repoList: List<NoteCategory> = listOf())
data class UpdatedNoteUiState(val note : Note = Note(0,"","",false,"",""))