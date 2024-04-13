package com.example.nyn.screens.updatescreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.nyn.screens.dialogs.ColorPickerDialog
import com.example.nyn.sharedui.SharedScaffold
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateNoteScreenScaffold(modifier: Modifier = Modifier,
                             updateScreenViewModel: UpdateScreenViewModel,
                             navHostController: NavHostController,
                             onShowSheet: () -> Unit,
                             onShowColorDialog : () -> Unit,
                             itemUiState : UpdatedNoteUiState){


    val coroutineScope = rememberCoroutineScope()
    val mContext = LocalContext.current


    if(!updateScreenViewModel.initExecuted.value)
    {
        updateScreenViewModel.noteTitle.value = itemUiState.note.title
        updateScreenViewModel.noteBody.value = itemUiState.note.body
        updateScreenViewModel.isPinned.value = itemUiState.note.isPinned
        updateScreenViewModel.setSelectedColor(itemUiState.note.color)
    }


    SharedScaffold(
        navHostController = navHostController,
        onShowSheet = onShowSheet,
        onShowColorDialog = onShowColorDialog,
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
        pinStateProvider = { updateScreenViewModel.getPinState() },
        noteColorProvider = { updateScreenViewModel.getSelectedColor() },
        enableShareNoteBody = true,
        onClickShareNote = {
            mContext.startActivity(updateScreenViewModel.getNoteBodyIntent())
        }
    )
}


@Composable
fun UpdateNoteScreen(modifier: Modifier = Modifier,
                  navHostController: NavHostController,
                  updateScreenViewModel: UpdateScreenViewModel,){


    var showSheet by rememberSaveable { mutableStateOf(false) }
    var openColorPickerAlert by remember { mutableStateOf(false) }
    val uiState = updateScreenViewModel.updatedNoteUiState.collectAsState()

//    if (showSheet) {
//        BottomSheet(modifier = modifier,
//            onDismiss = {showSheet = false},
//            addNoteViewModel = addNoteViewModel)
//    }

    if (openColorPickerAlert){
        ColorPickerDialog(onDismissRequest = { openColorPickerAlert = false }, onSetNoteColor = {updateScreenViewModel.setSelectedColor(it)})
    }

    UpdateNoteScreenScaffold( modifier = modifier,
        updateScreenViewModel = updateScreenViewModel,
        navHostController = navHostController,
        onShowSheet = { showSheet = true },
        itemUiState = uiState.value,
        onShowColorDialog = { openColorPickerAlert = true }
    )
}