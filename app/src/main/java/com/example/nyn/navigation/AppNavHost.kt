package com.example.nyn.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nyn.screens.homescreen.HomeScreenViewModel
import com.example.nyn.screens.addnotescreen.AddNoteScreen
import com.example.nyn.screens.addnotescreen.AddNoteViewModel
import com.example.nyn.screens.homescreen.HomeScreen
import com.example.nyn.screens.updatescreen.UpdateNoteScreen
import com.example.nyn.screens.updatescreen.UpdateScreenViewModel


@Composable
fun  AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.HomeScreen.route,

) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavigationItem.HomeScreen.route,) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navHostController = navController,homeScreenViewModel = viewModel)
        }
        composable(route = NavigationItem.AddNoteScreen.route,

            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            ) {
            val viewModel = hiltViewModel<AddNoteViewModel>()
            AddNoteScreen(navHostController = navController,
                addNoteViewModel = viewModel)
        }

        composable(route = NavigationItem.UpdateNoteScreen.route,

            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },

            arguments = listOf(navArgument("noteId") { type = NavType.StringType })

        ) {
            val viewModel = hiltViewModel<UpdateScreenViewModel>()
            UpdateNoteScreen(navHostController = navController,
                updateScreenViewModel = viewModel,)
        }
    }
}