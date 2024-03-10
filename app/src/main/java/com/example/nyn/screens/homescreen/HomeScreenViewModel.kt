package com.example.nyn.screens.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
import com.example.nyn.screens.addnotescreen.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repo: OfflineNotesRepository,
    private val categoryRepo : OfflineCategoriesRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val notesUiState: StateFlow<NoteUiState> =
        repo.getAllNotesStream().map { NoteUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = NoteUiState()
            )

    val categoriesUiState: StateFlow<CategoriesUiState> =
        categoryRepo.getAllNoteCategoriesStream().map { CategoriesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoriesUiState()
            )


    fun getCategoriesCount(categoriesList :List<NoteCategory>,notesList : List<Note>) : List<CategoryOccurrence>{
        val occurrencesMap = mutableMapOf<String,Int>()
        val listOfAllCategories = categoriesList.map { it.name }.toList()
        val listOfUsedCategories = notesList.map { it.category }.toList()

        for (item in listOfAllCategories){
            val occurrence = listOfUsedCategories.count {it == item}
            occurrencesMap[item] = occurrence
        }
        return occurrencesMap.map { CategoryOccurrence(it.key,it.value) }
    }

}

/**
 * Ui State for NoteCard
 */
data class NoteUiState(val notesList: List<Note> = listOf())
data class CategoryOccurrence(val categoryName : String, val count : Int)