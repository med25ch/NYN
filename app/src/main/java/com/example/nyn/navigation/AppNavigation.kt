package com.example.nyn.navigation

enum class Screen {
    HOME,
    ADD_NOTE,
}
sealed class NavigationItem(val route: String) {
    object HomeScreen : NavigationItem(Screen.HOME.name)
    object AddNoteScreen : NavigationItem(Screen.ADD_NOTE.name)
}