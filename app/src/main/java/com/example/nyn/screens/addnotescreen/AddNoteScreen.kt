package com.example.nyn.screens.addnotescreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.nyn.screens.dialogscreen.ColorPickerDialog
import com.example.nyn.sharedui.SharedScaffold
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNoteScreenScaffold(modifier: Modifier = Modifier,
                          addNoteViewModel: AddNoteViewModel,
                          navHostController: NavHostController,
                          onShowColorDialog: () -> Unit,
                          onShowSheet: () -> Unit){

    val coroutineScope = rememberCoroutineScope()

    SharedScaffold(
        navHostController = navHostController,
        onShowSheet = onShowSheet,
        onShowColorDialog = onShowColorDialog,
        modifier = modifier,
        onClickPinnedNote =  { addNoteViewModel.updatePinState() },
        onClickSaveNote = {
            coroutineScope.launch {
                addNoteViewModel.saveNoteToDB()
            }
        },
        onTitleTextValueChange = { addNoteViewModel.updateNoteTitle(it) } ,
        titleTextProvider = { addNoteViewModel.noteTitle.value },
        onBodyTextValueChange = { addNoteViewModel.updateNoteBody(it)},
        bodyTextProvider = { addNoteViewModel.noteBody.value },
        pinStateProvider = { addNoteViewModel.isPinned.value }
    )
}


@Composable
fun AddNoteScreen(modifier: Modifier = Modifier,
                  navHostController: NavHostController,
                  addNoteViewModel: AddNoteViewModel){


    var showSheet by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var openColorPickerAlert by remember { mutableStateOf(false) }


    if (openColorPickerAlert){
        ColorPickerDialog(
            onDismissRequest = { openColorPickerAlert = false },
            onSetNoteColor = { addNoteViewModel.setSelectedColor(it) }
            )
    }

    if (showSheet) {
        BottomSheet(modifier = modifier,
            onDismiss = {showSheet = false},
            addNoteViewModel = addNoteViewModel,
            onSaveCategory = {
                coroutineScope.launch { addNoteViewModel.saveCategoryToDB(it) }
            },
            onSetCategory = { addNoteViewModel.setSelectedCategoryName(it)},
            onDeleteCategory = {
                coroutineScope.launch { addNoteViewModel.deleteCategoryFromDB(it,true) }
            })
    }
    AddNoteScreenScaffold( modifier = modifier,
        addNoteViewModel = addNoteViewModel,
        navHostController = navHostController,
        onShowSheet = { showSheet = true },
        onShowColorDialog = { openColorPickerAlert = true }
        )
}


