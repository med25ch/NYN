package com.example.nyn.screens.addnotescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nyn.ui.theme.Sen
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreenScaffold(modifier: Modifier = Modifier,
                          addNoteViewModel: AddNoteViewModel,
                          navHostController: NavHostController,
                          onShowSheet: () -> Unit){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()
    var isPinned by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onShowSheet() }) {
                        Icon(
                            imageVector = Icons.Outlined.CreateNewFolder,
                            contentDescription = "Localized description"
                        )
                    }

                    IconButton(onClick = {
                        isPinned = !isPinned
                        addNoteViewModel.isPinnedNote(isPinned)
                    }) {
                        Icon(
                            imageVector = if (isPinned)
                                Icons.Filled.PushPin
                            else
                                Icons.Outlined.PushPin
                                ,
                            modifier = modifier.rotate(45f),
                            contentDescription = "Localized description"
                        )
                    }

                    IconButton(onClick = {
                        coroutineScope.launch {
                            addNoteViewModel.saveNoteToDB()
                        }
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Save,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
        ) {
            // Note title Text
            TitleNoteText(modifier,addNoteViewModel)

            // Note Body
            BodyNoteText(modifier,addNoteViewModel)
        }
    }
}

@Composable
fun AddNoteScreen(modifier: Modifier = Modifier,
                  navHostController: NavHostController,
                  addNoteViewModel: AddNoteViewModel){

    var showSheet by rememberSaveable { mutableStateOf(false) }

    if (showSheet) {
        BottomSheet(modifier = modifier,
            onDismiss = {showSheet = false},
            addNoteViewModel = addNoteViewModel)
    }
    AddNoteScreenScaffold( modifier = modifier,
        addNoteViewModel = addNoteViewModel,
        navHostController = navHostController,
        onShowSheet = { showSheet = true })
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleNoteText(modifier: Modifier, addNoteViewModel: AddNoteViewModel) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = addNoteViewModel.noteTitle.value ,
        onValueChange = { addNoteViewModel.updateNoteTitle(it) },
        label = { Text("Note title") },
        maxLines = 3,
        singleLine = false,
        textStyle = TextStyle(
            fontFamily = Sen,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
        ),
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
        )
    )
}

@Composable
fun BodyNoteText(modifier: Modifier = Modifier, addNoteViewModel: AddNoteViewModel) {

    TextField(
        value = addNoteViewModel.noteBody.value,
        onValueChange = { addNoteViewModel.updateNoteBody(it) },
        label = { Text("Note body") },
        maxLines = 50,
        textStyle = TextStyle(
            fontFamily = Sen,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        ),
        modifier = Modifier
            .fillMaxSize(),
        singleLine = false,
        minLines = 15,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
        )
    )
}