package com.example.nyn.screens.homescreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.nyn.categoryui.CategoriesLazyRow
import com.example.nyn.navigation.Screen
import com.example.nyn.noteui.NoteCard
import com.example.nyn.ui.theme.CustomLightGray
import com.example.nyn.ui.theme.Sen
import com.example.nyn.ui.theme.VeryLightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldNYN(modifier: Modifier = Modifier,
                navHostController: NavHostController,
                homeScreenViewModel: HomeScreenViewModel
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
                 SmallTopAppBar(modifier,scrollBehavior)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate(Screen.ADD_NOTE.name) },
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            // SearchBar
            SearchBar(modifier, homeScreenViewModel)

            // LazyRow of Categories
            CategoriesLazyRow(modifier,homeScreenViewModel)

            // Staggered Grid of notes
            NotesLazyStaggeredGrid(modifier,homeScreenViewModel,navHostController)
        }
    }
}


@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               homeScreenViewModel: HomeScreenViewModel,
               navHostController: NavHostController
){

    ScaffoldNYN(modifier = modifier,
        homeScreenViewModel = homeScreenViewModel,
        navHostController = navHostController)
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
fun SearchBar(modifier: Modifier = Modifier, homeScreenViewModel: HomeScreenViewModel){
    // this is the text users enter
    var queryString by remember {
        mutableStateOf("")
    }

    // if the search bar is active or not
    var isActive by remember {
        mutableStateOf(false)
    }

    val contextForToast = LocalContext.current.applicationContext

    //Update the search query
    homeScreenViewModel.updateSearchQuery(queryString)

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

@Composable
fun NotesLazyStaggeredGrid(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel,
    navHostController: NavHostController){

    val allNotesUiState by homeScreenViewModel.notesUiFlowCombined.collectAsState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(items = allNotesUiState.notesList) { note ->
                NoteCard(note = note, onCardClick = { navHostController.navigate(Screen.UPDATE_NOTE.name + "/${note.id}") })
            }
        }
    )
}

