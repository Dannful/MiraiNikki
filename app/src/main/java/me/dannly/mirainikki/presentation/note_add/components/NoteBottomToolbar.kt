package me.dannly.mirainikki.presentation.note_add.components

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.core.components.NoteAttachmentsRow
import me.dannly.mirainikki.presentation.note_add.NoteAddEvent
import me.dannly.mirainikki.presentation.note_add.NoteAddViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteBottomToolbar(
    modifier: Modifier = Modifier,
    noteAddViewModel: NoteAddViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val contract =
        rememberLauncherForActivityResult(
            contract =
            ActivityResultContracts.OpenDocument()
        ) {
            if (it != null) {
                noteAddViewModel.onEvent(NoteAddEvent.AddAttachment(it))
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
    LaunchedEffect(key1 = Unit) {
        noteAddViewModel.uiEvent.mapNotNull { it as? NoteAddEvent.UiEvent.LaunchContract }.collect {
            contract.launch(arrayOf("image/*", "video/*"))
        }
    }
    Column(verticalArrangement = Arrangement.Bottom, modifier = modifier) {
        NoteAttachmentsRow(
            uris = noteAddViewModel.note.attachments,
            modifier = Modifier.fillMaxWidth(),
            imageSize = 64.dp
        ) {
            noteAddViewModel.onEvent(NoteAddEvent.RemoveAttachment(it))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Surface(elevation = 4.dp) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        noteAddViewModel.sendUiEvent(
                            NoteAddEvent.UiEvent.SetDrawerValue(
                                BottomDrawerValue.Open
                            )
                        )
                    }
                ) {
                    Icon(
                        contentDescription = stringResource(R.string.more_options),
                        imageVector = Icons.Default.Add
                    )
                }
            }
        }
    }
}