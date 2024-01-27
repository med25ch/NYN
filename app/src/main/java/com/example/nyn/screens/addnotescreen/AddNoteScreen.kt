package com.example.nyn.screens.addnotescreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
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
fun AddNoteScreenScaffold(modifier: Modifier = Modifier,
                          addNoteViewModel: AddNoteViewModel,
                          navHostController: NavHostController,
                          onShowSheet: () -> Unit){

    val coroutineScope = rememberCoroutineScope()

    SharedScaffold(
        navHostController = navHostController,
        onShowSheet = onShowSheet,
        modifier = modifier,
        onClickPinnedNote =  { addNoteViewModel.isPinnedNote(it) },
        onClickSaveNote = {
            coroutineScope.launch {
                addNoteViewModel.saveNoteToDB()
            }
        },
        onTitleTextValueChange = { addNoteViewModel.updateNoteTitle(it) } ,
        titleTextProvider = { addNoteViewModel.noteTitle.value },
        onBodyTextValueChange = { addNoteViewModel.updateNoteBody(it)},
        bodyTextProvider = { addNoteViewModel.noteBody.value }
    )
}


@Composable
fun AddNoteScreen(modifier: Modifier = Modifier,
                  navHostController: NavHostController,
                  addNoteViewModel: AddNoteViewModel,
                  argument : String?){


    val noteId = argument?.toIntOrNull()

    if (noteId != null) {
        addNoteViewModel.updateNoteMode(true)
        addNoteViewModel.setNoteIdToUpdate(noteId)
    }

    var showSheet by rememberSaveable { mutableStateOf(false) }

    if (showSheet) {
        BottomSheet(modifier = modifier,
            onDismiss = {showSheet = false},
            addNoteViewModel = addNoteViewModel)
    }
    AddNoteScreenScaffold( modifier = modifier,
        addNoteViewModel = addNoteViewModel,
        navHostController = navHostController,
        onShowSheet = { showSheet = true },
        )
}


