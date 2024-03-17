package com.example.nyn.screens.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.constants.DataConstants.ALL_NOTES
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
import com.example.nyn.di.UserPreferences
import com.example.nyn.di.UserPreferencesRepository
import com.example.nyn.screens.addnotescreen.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repo: OfflineNotesRepository,
    private val categoryRepo : OfflineCategoriesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

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

    val sortingUiState: StateFlow<UserPreferences> =
        userPreferencesRepository.userPreferencesFlow.map { UserPreferences(it.sortCategoryString) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserPreferences()
            )

    fun getCategoriesCount(categoriesList :List<NoteCategory>,notesList : List<Note>) : List<CategoryOccurrence>{
        val occurrencesMap = mutableMapOf<String,Int>()
        val listOfAllCategories = categoriesList.map { it.name }.toList()
        val listOfUsedCategories = notesList.map { it.category }.toList()

        // Add All notes to be an option to show all notes without category filter
        occurrencesMap[ALL_NOTES] = notesList.size

        for (item in listOfAllCategories){
            val occurrence = listOfUsedCategories.count {it == item}
            occurrencesMap[item] = occurrence
        }
        return occurrencesMap.map { CategoryOccurrence(it.key,it.value) }
    }

    fun updateCategorySorting(category: String){
        viewModelScope.launch {
            userPreferencesRepository.updateCategorySort(category)
        }
    }

    val notesUiFlowCombined : StateFlow<NoteUiStateFil> = notesUiState.combine(sortingUiState)
    { notesUiState,userPreferences ->

        if (userPreferences.sortCategoryString == ALL_NOTES){
            return@combine NoteUiStateFil(
                notesList = notesUiState.notesList,
                category = userPreferences.sortCategoryString)
        }else{
            return@combine NoteUiStateFil(
                notesList = notesUiState.notesList.filter { it.category == userPreferences.sortCategoryString },
                category = userPreferences.sortCategoryString)
        }

    }.stateIn(viewModelScope,SharingStarted.Eagerly,NoteUiStateFil())


}

/**
 * Ui State for NoteCard
 */
data class NoteUiState(val notesList: List<Note> = listOf())
data class CategoryOccurrence(val categoryName : String, val count : Int)
data class NoteUiStateFil(val notesList: List<Note> = listOf(), val category: String = "")
