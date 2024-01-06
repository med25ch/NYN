package com.example.nyn.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nyn.noteui.HomeScreenViewModel
import com.example.nyn.screens.AddNoteScreen
import com.example.nyn.screens.HomeScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.HomeScreen.route,

) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navHostController = navController,homeScreenViewModel = viewModel)
        }
        composable(NavigationItem.AddNoteScreen.route) {
            AddNoteScreen(navHostController = navController)
        }
    }
}