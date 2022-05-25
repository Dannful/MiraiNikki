package me.dannly.mirainikki.presentation.note_add.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import me.dannly.mirainikki.R
import me.dannly.mirainikki.navigation.Routes
import me.dannly.mirainikki.navigation.toNavViewModel
import me.dannly.mirainikki.presentation.note_add.NoteAddEvent
import me.dannly.mirainikki.presentation.note_add.NoteAddViewModel
import me.dannly.mirainikki.ui.theme.LocalNavigation

val noteAddActions: @Composable RowScope.() -> Unit = {
    val viewModel: NoteAddViewModel =
        "${Routes.ADD_NOTE}?${Routes.ADD_NOTE_ID_ARGUMENT}={${Routes.ADD_NOTE_ID_ARGUMENT}}".toNavViewModel()
    var dialogVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val controller = LocalNavigation.current
    NotePickDateDialog(noteAddViewModel = viewModel, visible = dialogVisible) {
        dialogVisible = false
    }
    IconButton(
        onClick = {
            dialogVisible = true
        }
    ) {
        Icon(
            contentDescription = stringResource(R.string.pick_date_time),
            painter = painterResource(id = R.drawable.ic_baseline_access_time_24)
        )
    }
    IconButton(
        enabled = viewModel.note.title.isNotBlank(),
        onClick = {
            viewModel.onEvent(NoteAddEvent.Save)
            controller.popBackStack()
        }
    ) {
        Icon(
            contentDescription = "Save",
            painter = painterResource(id = R.drawable.ic_baseline_save_24)
        )
    }
}