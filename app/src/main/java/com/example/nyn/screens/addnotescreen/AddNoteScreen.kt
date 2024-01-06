package com.example.nyn.screens.addnotescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.nyn.ui.theme.Sen
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreenScaffold(modifier: Modifier = Modifier,
                          addNoteViewModel: AddNoteViewModel,
                          navHostController: NavHostController,){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()

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
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.CreateNewFolder,
                            contentDescription = "Localized description"
                        )
                    }

                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.PushPin,
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
                            imageVector = Icons.Filled.Save,
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

    AddNoteScreenScaffold( modifier = modifier,
        addNoteViewModel = addNoteViewModel,
        navHostController = navHostController)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleNoteText(modifier: Modifier, addNoteViewModel: AddNoteViewModel) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = addNoteViewModel.noteTitle.value ,
        onValueChange = { addNoteViewModel.updateNoteTitle(it) },
        label = { Text("Note title") },
        maxLines = 1,
        textStyle = TextStyle(
            fontFamily = Sen,
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

    var value by remember { mutableStateOf("") }

    TextField(
        value = addNoteViewModel.noteBody.value,
        onValueChange = { addNoteViewModel.updateNoteBody(it) },
        label = { Text("Note body") },
        maxLines = 50,
        textStyle = TextStyle(
            fontFamily = Sen,
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