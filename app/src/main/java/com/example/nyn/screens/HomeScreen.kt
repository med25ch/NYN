package com.example.nyn.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyn.noteui.NotesLazyStaggeredGrid
import com.example.nyn.ui.theme.CustomLightGray
import com.example.nyn.ui.theme.NYNTheme
import com.example.nyn.ui.theme.Sen
import com.example.nyn.ui.theme.VeryLightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldNYN(modifier: Modifier = Modifier) {
    var presses by remember { mutableIntStateOf(0) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
                 SmallTopAppBar(modifier,scrollBehavior)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ },
                containerColor = Color.DarkGray,
                contentColor = Color.White,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            // SearchBar
            SearchBar(modifier)

            // Staggered Grid of notes
            NotesLazyStaggeredGrid(modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBar(modifier: Modifier = Modifier, scrollBehavior: TopAppBarScrollBehavior){
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Notes you need",
                fontFamily = Sen,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal)
        },

        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Localized description"
                )
            }
        },

        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier){
    // this is the text users enter
    var queryString by remember {
        mutableStateOf("")
    }

    // if the search bar is active or not
    var isActive by remember {
        mutableStateOf(false)
    }

    val contextForToast = LocalContext.current.applicationContext

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
        ,
        query = queryString,
        onQueryChange = { newQueryString ->
            queryString = newQueryString
        },
        onSearch = {
            isActive = false
            Toast.makeText(contextForToast, "Your query string: $queryString", Toast.LENGTH_SHORT)
                .show()
        },
        active = false,
        onActiveChange = { activeChange ->
            isActive = activeChange
        },
        placeholder = {
            Text(text = "Search for notes",
                fontFamily = Sen,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Normal,
                color = CustomLightGray,
                modifier = modifier.height(16.dp))
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = CustomLightGray)
        },
        colors = SearchBarDefaults.colors(
            containerColor = VeryLightGray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun NynAppPreview() {
    NYNTheme {
        //ScaffoldNYN()
    }
}