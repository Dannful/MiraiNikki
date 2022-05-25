package me.dannly.mirainikki.presentation.note_list.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.core.components.NoteAttachmentsRow
import me.dannly.mirainikki.presentation.note_list.NoteListViewModel
import me.dannly.mirainikki.presentation.note_list.util.formatDateTime
import me.dannly.mirainikki.ui.theme.LocalScaffoldState
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteItem(
    index: Int,
    noteListViewModel: NoteListViewModel,
    onNoteClick: (Int) -> Unit
) {
    val note = noteListViewModel.noteList[index]
    val state = rememberSwipeableState(initialValue = 0)
    val widthInPx =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .offset {
                IntOffset(state.offset.value.roundToInt(), 0)
            }
            .border(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.24f),
                width = 1.dp
            )
            .swipeable(
                state = state, anchors = mapOf(
                    0f to 0,
                    widthInPx to 1
                ), orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.3f) }
            )
            .clickable {
                onNoteClick(note.id!!)
            }
            .padding(8.dp)
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = note.text,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body2
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            NoteAttachmentsRow(uris = note.attachments, imageSize = 32.dp)
            Text(
                text = buildString {
                    if (note.to == null) {
                        append(
                            formatDateTime(
                                epoch = note.from,
                                current = System.currentTimeMillis()
                            )
                        )
                        return@buildString
                    }
                    append(
                        "${
                            formatDateTime(
                                epoch = note.from,
                                current = System.currentTimeMillis()
                            )
                        } - ${
                            formatDateTime(
                                epoch = note.to,
                                current = System.currentTimeMillis()
                            )
                        }"
                    )
                },
                style = MaterialTheme.typography.caption,
                fontStyle = FontStyle.Italic
            )
        }
    }
    val scaffoldState = LocalScaffoldState.current
    val deletedMessage = stringResource(R.string.note_deleted)
    val undoMessage = stringResource(R.string.undo)
    LaunchedEffect(key1 = state.currentValue) {
        when (state.currentValue) {
            1 -> {
                noteListViewModel.deleteNote(scaffoldState, deletedMessage, undoMessage, index)
                state.snapTo(0)
            }
        }
    }
}