package me.dannly.mirainikki.presentation.note_list.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.mirainikki.R

@Composable
inline fun AddNoteFab(crossinline onClick: () -> Unit) {
    FloatingActionButton(onClick = {
        onClick()
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_note)
        )
    }
}