package me.dannly.mirainikki.presentation.note_add.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import me.dannly.mirainikki.R
import me.dannly.mirainikki.presentation.note_add.NoteAddEvent
import me.dannly.mirainikki.presentation.note_add.NoteAddViewModel
import java.util.*

@Composable
fun NotePickDateDialog(
    noteAddViewModel: NoteAddViewModel,
    visible: Boolean,
    onDismiss: () -> Unit
) {
    var currentlySelectedDateType by rememberSaveable {
        mutableStateOf(0)
    }
    val updateCurrentValue: (Long) -> Unit = {
        noteAddViewModel.onEvent(
            if (currentlySelectedDateType == 0) {
                val current = Calendar.getInstance()
                val selected = Calendar.getInstance().apply {
                    timeInMillis = it
                }
                NoteAddEvent.SetFrom(if (selected.get(Calendar.DAY_OF_YEAR) >= current.get(Calendar.DAY_OF_YEAR)) it else noteAddViewModel.note.from)
            } else {
                NoteAddEvent.SetTo(
                    if (it > noteAddViewModel.note.from)
                        it
                    else null
                )
            }
        )
    }
    val currentValue =
        if (currentlySelectedDateType == 0) noteAddViewModel.note.from else noteAddViewModel.note.to
    val datePicker =
        noteDatePicker(currentMillis = currentValue ?: System.currentTimeMillis()) {
            updateCurrentValue(it)
        }
    val timePicker = noteTimePicker(currentMillis = currentValue ?: System.currentTimeMillis()) {
        updateCurrentValue(it)
    }
    if (visible)
        Dialog(onDismissRequest = onDismiss) {
            Surface(shape = MaterialTheme.shapes.medium) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.adjust_date_and_time),
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.SemiBold
                    )
                    ChipRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        individualChipModifier = Modifier.weight(1f),
                        currentSelection = currentlySelectedDateType,
                        elements = listOf(
                            stringResource(R.string.from),
                            stringResource(R.string.to)
                        )
                    ) {
                        currentlySelectedDateType = it
                    }
                    NotePickDateDialog(
                        currentValue = currentValue,
                        datePicker = datePicker,
                        updateCurrentValue = updateCurrentValue
                    )
                    NotePickTimeDialog(
                        from = noteAddViewModel.note.from,
                        currentlySelectedDateType = currentlySelectedDateType,
                        currentValue = currentValue,
                        timePickerDialog = timePicker,
                        updateCurrentValue = updateCurrentValue
                    )
                    TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
                        Text(text = stringResource(R.string.ok))
                    }
                }
            }
        }
}