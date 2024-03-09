package com.example.nyn.sharedui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material.icons.outlined.FormatColorFill
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.FlagCircle
import androidx.compose.material.icons.twotone.Circle
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nyn.ui.theme.Sen

@Composable
fun TitleNoteText(modifier: Modifier,
                  onTitleTextValueChange : (String) -> Unit,
                  titleTextProvider : () -> String
                  ) {

    TextField(
        value = titleTextProvider() ,
        onValueChange = { onTitleTextValueChange(it) },
        label = { Text("Note title") },
        maxLines = 3,
        singleLine = false,
        textStyle = TextStyle(
            fontFamily = Sen,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
        ),
        modifier = modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun BodyNoteText(modifier: Modifier = Modifier,
                 onBodyTextValueChange : (String) -> Unit,
                 bodyTextProvider : () -> String) {

    TextField(
        value = bodyTextProvider(),
        onValueChange = { onBodyTextValueChange(it) },
        label = { Text("Note body") },
        maxLines = 50,
        textStyle = TextStyle(
            fontFamily = Sen,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        ),
        modifier = modifier
            .fillMaxSize(),
        singleLine = false,
        minLines = 15,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SharedScaffold(
    navHostController: NavHostController,
    onShowSheet: () -> Unit,
    onShowColorDialog: () -> Unit,
    modifier: Modifier,
    onClickPinnedNote: () -> Unit,
    onClickSaveNote: () -> Unit,
    onTitleTextValueChange : (String) -> Unit,
    titleTextProvider : () -> String,
    onBodyTextValueChange : (String) -> Unit,
    bodyTextProvider : () -> String,
    pinStateProvider : () -> Boolean,
    noteColorProvider : () -> Long
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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

                    IconButton(onClick = { onShowColorDialog() }) {
                        Icon(
                            imageVector = Icons.TwoTone.Circle,
                            contentDescription = "Localized description",
                            tint = if (noteColorProvider() != 0L) Color(noteColorProvider()) else Color(0xffc2dcfd)
                        )
                    }

                    IconButton(onClick = {
                        onClickPinnedNote()
                    }) {
                        Icon(
                            imageVector = if (pinStateProvider())
                                Icons.Filled.PushPin
                            else
                                Icons.Outlined.PushPin,
                            modifier = modifier.rotate(45f),
                            contentDescription = "Localized description"
                        )
                    }

                    IconButton(onClick = {
                        onClickSaveNote()
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
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
        ) {

            // Note title Text
            TitleNoteText(
                modifier,
                onTitleTextValueChange = {onTitleTextValueChange(it)},
                titleTextProvider = titleTextProvider
            )

            // Note Body
            BodyNoteText(
                modifier,
                onBodyTextValueChange = { onBodyTextValueChange(it) },
                bodyTextProvider =  bodyTextProvider
            )
        }
    }
}