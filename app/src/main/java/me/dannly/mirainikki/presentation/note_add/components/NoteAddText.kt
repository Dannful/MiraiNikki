package me.dannly.mirainikki.presentation.note_add.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.note_add.NoteAddEvent
import me.dannly.mirainikki.presentation.note_add.NoteAddViewModel

@Composable
fun NoteAddText(
    modifier: Modifier = Modifier,
    noteAddViewModel: NoteAddViewModel,
    textFieldColors: TextFieldColors
) {
    val textStyle = MaterialTheme.typography.body1.copy(
        textAlign = TextAlign.Justify
    )
    TextField(
        colors = textFieldColors,
        textStyle = textStyle,
        placeholder = {
            Text(text = stringResource(R.string.note), style = textStyle)
        },
        value = noteAddViewModel.note.text,
        onValueChange = {
            noteAddViewModel.onEvent(NoteAddEvent.SetText(it))
        }, modifier = modifier
    )
}