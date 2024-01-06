package com.example.nyn.screens.addnotescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineNotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val repo: OfflineNotesRepository) : ViewModel() {

    // Backing property to avoid state updates from other classes
//    private val _uiState = MutableStateFlow(NoteUiState())
//    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    // MutableState to handle our UI state
    var noteTitle = mutableStateOf("")
    var noteBody = mutableStateOf("")


    suspend fun saveNoteToDB(){

        // Todo : Validate note before saving

        val note = Note(id = 0,
            title = noteTitle.value,
            body = noteBody.value,
            isPinned = false,
            category = "",
            color = "")
        repo.insertNote(note)
    }

    fun updateNoteTitle(title : String){
        noteTitle.value = title
    }

    fun updateNoteBody(body : String){
        noteBody.value = body
    }

}