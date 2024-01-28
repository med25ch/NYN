package com.example.nyn.screens.updatescreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.nyn.sharedui.SharedScaffold
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateNoteScreenScaffold(modifier: Modifier = Modifier,
                             updateScreenViewModel: UpdateScreenViewModel,
                             navHostController: NavHostController,
                             onShowSheet: () -> Unit,
                             itemUiState : UpdatedNoteUiState){


    val coroutineScope = rememberCoroutineScope()

    updateScreenViewModel.noteTitle.value = itemUiState.note.title
    updateScreenViewModel.noteBody.value = itemUiState.note.body
    updateScreenViewModel.isPinned.value = itemUiState.note.isPinned


    SharedScaffold(
        navHostController = navHostController,
        onShowSheet = onShowSheet,
        modifier = modifier,
        onClickPinnedNote =  { updateScreenViewModel.updatePinState()},
        onClickSaveNote = {
            coroutineScope.launch {
                updateScreenViewModel.updateNote(itemUiState.note)
            }
        },
        onTitleTextValueChange = { updateScreenViewModel.updateTitle(it) } ,
        titleTextProvider = { updateScreenViewModel.getNoteTitle() },
        onBodyTextValueChange = { updateScreenViewModel.updateNoteBody(it) },
        bodyTextProvider = { updateScreenViewModel.getNoteBody() },
        pinStateProvider = { updateScreenViewModel.getPinState() }
    )
}


@Composable
fun UpdateNoteScreen(modifier: Modifier = Modifier,
                  navHostController: NavHostController,
                  updateScreenViewModel: UpdateScreenViewModel, ){


    var showSheet by rememberSaveable { mutableStateOf(false) }

    val uiState = updateScreenViewModel.updatedNoteUiState.collectAsState()

//    if (showSheet) {
//        BottomSheet(modifier = modifier,
//            onDismiss = {showSheet = false},
//            addNoteViewModel = addNoteViewModel)
//    }

    UpdateNoteScreenScaffold( modifier = modifier,
        updateScreenViewModel = updateScreenViewModel,
        navHostController = navHostController,
        onShowSheet = { showSheet = true },
        itemUiState = uiState.value
    )
}