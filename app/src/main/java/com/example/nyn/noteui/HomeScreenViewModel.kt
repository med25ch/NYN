package com.example.nyn.noteui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyn.data.models.note.Note
import com.example.nyn.data.repositories.OfflineNotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repo: OfflineNotesRepository) : ViewModel() {

    val homeUiState: StateFlow<NoteUiState> =
        repo.getAllNotesStream().map { NoteUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = NoteUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

/**
 * Ui State for NoteCard
 */
data class NoteUiState(val notesList: List<Note> = listOf())