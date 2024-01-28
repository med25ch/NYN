package com.example.nyn.navigation

enum class Screen {
    HOME,
    ADD_NOTE,
    UPDATE_NOTE
}
sealed class NavigationItem(val route: String) {
    object HomeScreen : NavigationItem(Screen.HOME.name)
    object AddNoteScreen : NavigationItem(Screen.ADD_NOTE.name)
    object UpdateNoteScreen : NavigationItem(Screen.UPDATE_NOTE.name + "/{noteId}")
}