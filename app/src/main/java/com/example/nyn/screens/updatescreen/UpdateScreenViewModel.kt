package com.example.nyn.screens.updatescreen

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteRepo: OfflineNotesRepository,
    private val categoryRepo: OfflineCategoriesRepository
) : ViewModel() {

    // MutableState to handle our UI state
    var noteTitle = mutableStateOf("")
    var noteBody = mutableStateOf("")
    var selectedCategory = mutableStateOf("")
    private var selectedColor = mutableLongStateOf(0L)
    var isPinned = mutableStateOf(false)
    var initExecuted = mutableStateOf(false)

    private val noteIdToUpdate: String = checkNotNull(savedStateHandle["noteId"])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val updatedNoteUiState: StateFlow<UpdatedNoteUiState> =
        noteRepo.getNoteStream(noteIdToUpdate.toInt()).filterNotNull().map {
                UpdatedNoteUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UpdatedNoteUiState()
            )

    init {
        viewModelScope.launch {
            delay(7000)
            initExecuted.value = true
        }
    }

    fun getNoteTitle(): String {
        return noteTitle.value
    }

    fun updateTitle(title: String) {
        noteTitle.value = title
    }

    fun updateNoteBody(body: String) {
        noteBody.value = body
    }

    fun getNoteBody(): String {
        return noteBody.value
    }

    fun getPinState(): Boolean {
        return isPinned.value
    }

    fun updatePinState(): Boolean {
        isPinned.value = !isPinned.value
        return isPinned.value
    }

    suspend fun updateNote(noteToUpdate : Note) {

        noteToUpdate.title =  getNoteTitle()
        noteToUpdate.body = getNoteBody()
        noteToUpdate.isPinned = isPinned.value
        noteToUpdate.color = selectedColor.longValue

        noteRepo.updateNote(noteToUpdate)
    }

    fun setSelectedColor(it: Long) {
        selectedColor.longValue = it
    }

    fun getSelectedColor() : Long{
        return selectedColor.longValue
    }
}


data class UpdatedNoteUiState(val note: Note = Note(0, "", "", false, "", 0))