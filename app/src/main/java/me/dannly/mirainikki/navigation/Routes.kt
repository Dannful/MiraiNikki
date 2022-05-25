package me.dannly.mirainikki.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import me.dannly.mirainikki.ui.theme.LocalNavigation

object Routes {

    const val NOTES_LIST = "notes_list"

    const val ADD_NOTE = "add_note"
    const val ADD_NOTE_ID_ARGUMENT = "id"
}

@Composable
inline fun <reified T: ViewModel> String.toNavViewModel(): T {
    val controller = LocalNavigation.current
    return hiltViewModel(remember { controller.getBackStackEntry(this) })
}