package me.dannly.mirainikki.presentation.note_add

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.dannly.mirainikki.navigation.Routes
import me.dannly.mirainikki.navigation.toNavViewModel
import me.dannly.mirainikki.presentation.note_add.components.NoteAddText
import me.dannly.mirainikki.presentation.note_add.components.NoteAddTitle
import me.dannly.mirainikki.presentation.note_add.components.NoteBottomToolbar

@Composable
fun NoteAddScreen(
    noteAddViewModel: NoteAddViewModel = "${Routes.ADD_NOTE}?${Routes.ADD_NOTE_ID_ARGUMENT}={${Routes.ADD_NOTE_ID_ARGUMENT}}".toNavViewModel(),
    onAdd: () -> Unit
) {
    val textFieldColors =
        TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color.Gray
        )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        NoteAddTitle(noteAddViewModel = noteAddViewModel, textFieldColors = textFieldColors)
        NoteAddText(noteAddViewModel = noteAddViewModel, textFieldColors = textFieldColors)
        NoteBottomToolbar(modifier = Modifier.fillMaxSize())
    }
}