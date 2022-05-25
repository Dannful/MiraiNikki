package me.dannly.mirainikki.presentation.note_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.note_list.NoteListViewModel
import me.dannly.mirainikki.presentation.note_list.util.formatDate

@Composable
fun NoteListHeader(
    noteListViewModel: NoteListViewModel = hiltViewModel()
) {
    Surface(elevation = 4.dp) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val formattedDay = formatDate(epoch = noteListViewModel.day)
            IconButton(
                enabled = formattedDay != stringResource(id = R.string.today),
                onClick = noteListViewModel::previousDay
            ) {
                Icon(
                    contentDescription = stringResource(R.string.previous_day),
                    imageVector = Icons.Default.ArrowBack
                )
            }
            Text(
                text = formattedDay, style = MaterialTheme.typography.h4
            )
            IconButton(
                onClick = noteListViewModel::nextDay
            ) {
                Icon(
                    contentDescription = stringResource(R.string.next_day),
                    imageVector = Icons.Default.ArrowForward
                )
            }
        }
    }
}