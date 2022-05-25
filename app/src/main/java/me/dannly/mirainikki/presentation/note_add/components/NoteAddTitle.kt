package me.dannly.mirainikki.presentation.note_add.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.note_add.NoteAddEvent
import me.dannly.mirainikki.presentation.note_add.NoteAddViewModel

@Composable
fun NoteAddTitle(
    modifier: Modifier = Modifier,
    noteAddViewModel: NoteAddViewModel,
    textFieldColors: TextFieldColors
) {
    val textStyle = MaterialTheme.typography.h5
    TextField(
        colors = textFieldColors,
        textStyle = textStyle,
        placeholder = {
            Text(text = stringResource(id = R.string.title), style = textStyle)
        },
        value = noteAddViewModel.note.title,
        onValueChange = {
            noteAddViewModel.onEvent(
                NoteAddEvent.SetTitle(
                    it.substring(
                        0,
                        40.coerceAtMost(it.length)
                    )
                )
            )
        }, modifier = modifier
    )
}