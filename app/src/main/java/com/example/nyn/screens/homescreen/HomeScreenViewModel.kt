package com.example.nyn.screens.homescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.constants.DataConstants.ALL_NOTES
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
import com.example.nyn.di.UserPreferences
import com.example.nyn.di.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
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


    private val _searchQuery = MutableStateFlow("")

    private val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(query : String){
        _searchQuery.value = query
    }

    private val notesUiState: StateFlow<NoteUiState> =
        repo.getAllNotesStream().map { NoteUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = NoteUiState()
            )

    val categoriesUiState: StateFlow<CategoriesCountUiState> =
        categoryRepo.getAllNoteCategoriesStream().zip(repo.getAllNotesStream())
        { categoriesList,notesList ->
            val occurrencesMap = mutableMapOf<String,Int>()
            val listOfAllCategories = categoriesList.map { it.name }.toList()
            val listOfUsedCategories = notesList.map { it.category }.toList()

            // Add All notes to be an option to show all notes without category filter
            occurrencesMap[ALL_NOTES] = notesList.size

            for (item in listOfAllCategories){
                val occurrence = listOfUsedCategories.count {it == item}
                occurrencesMap[item] = occurrence
            }
            return@zip occurrencesMap.map { CategoryOccurrence(it.key,it.value) }
        }
            .map { CategoriesCountUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoriesCountUiState()
            )

    val sortingUiState: StateFlow<UserPreferences> =
        userPreferencesRepository.userPreferencesFlow.map { UserPreferences(it.sortCategoryString) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserPreferences()
            )

    fun updateCategorySorting(category: String){
        viewModelScope.launch {
            userPreferencesRepository.updateCategorySort(category)
        }
    }

    val notesUiFlowCombined : StateFlow<NoteUiStateFil> = combine(notesUiState,sortingUiState,searchQuery)
    { notesUiState,userPreferences , searchQuery ->

        if (userPreferences.sortCategoryString == ALL_NOTES){
            return@combine NoteUiStateFil(
                notesList = notesUiState.notesList.filter { it.body.contains(searchQuery,true) },
                category = userPreferences.sortCategoryString)
        }else{
            return@combine NoteUiStateFil(
                notesList = notesUiState.notesList.filter {
                    it.category == userPreferences.sortCategoryString && it.body.contains(searchQuery,true)},
                category = userPreferences.sortCategoryString)
        }

    }.stateIn(viewModelScope,SharingStarted.Eagerly,NoteUiStateFil())


}



data class NoteUiState(val notesList: List<Note> = listOf())

data class CategoriesCountUiState(val categoryOccurrenceList: List<CategoryOccurrence> = listOf())
data class CategoryOccurrence(val categoryName : String, val count : Int)
data class NoteUiStateFil(val notesList: List<Note> = listOf(), val category: String = "")
