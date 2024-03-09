package com.example.nyn.screens.addnotescreen

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
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

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    // MutableState to handle our UI state
    var noteTitle = mutableStateOf("")
    var noteBody = mutableStateOf("")
    private var selectedCategory = mutableStateOf("")
    private var selectedColor = mutableLongStateOf(0L)
    var isPinned = mutableStateOf(false)


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

    suspend fun saveNoteToDB(){

        // Todo : Validate note before saving

            val note = Note(id = 0,
                title = noteTitle.value,
                body = noteBody.value,
                isPinned = isPinned.value,
                category = getSelectedCategoryName(),
                color = selectedColor.longValue)
            noteRepo.insertNote(note)
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

    fun updatePinState() : Boolean{
        isPinned.value = ! isPinned.value
        return isPinned.value
    }

    fun setSelectedColor(it: Long) {
        selectedColor.longValue = it
    }

}

data class CategoriesUiState(val repoList: List<NoteCategory> = listOf())