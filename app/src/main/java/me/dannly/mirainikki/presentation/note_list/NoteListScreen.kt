package me.dannly.mirainikki.presentation.note_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.mirainikki.presentation.note_list.components.NoteItem
import me.dannly.mirainikki.presentation.note_list.components.NoteListHeader

@Composable
fun NoteListScreen(
    noteListViewModel: NoteListViewModel = hiltViewModel(),
    onNoteClick: (Int) -> Unit
) {
    Column {
        NoteListHeader(noteListViewModel = noteListViewModel)
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items = noteListViewModel.noteList, key = { index, note ->
                note.id ?: index
            }) { index, _ ->
                NoteItem(
                    index = index,
                    noteListViewModel = noteListViewModel,
                    onNoteClick = onNoteClick
                )
            }
        }
    }
}