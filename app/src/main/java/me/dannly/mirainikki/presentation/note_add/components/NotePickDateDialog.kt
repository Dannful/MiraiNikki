package me.dannly.mirainikki.presentation.note_add.components

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.mirainikki.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotePickDateDialog(
    currentValue: Long?,
    datePicker: DatePickerDialog,
    updateCurrentValue: (Long) -> Unit
) {
    NoteDialogDropdownMenu(
        items = listOf(
            stringResource(id = R.string.today),
            stringResource(id = R.string.tomorrow),
            stringResource(id = R.string.custom_date)
        ),
        selectedText = if (currentValue != null) SimpleDateFormat(
            "dd MMM",
            Locale.getDefault()
        ).format(
            Date(currentValue)
        ) else "-"
    ) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentValue ?: System.currentTimeMillis()
        }
        val current = Calendar.getInstance()
        val currentDay = current.get(Calendar.DATE)
        when (it) {
            0 -> {
                calendar.set(Calendar.DATE, currentDay)
                updateCurrentValue(calendar.timeInMillis)
            }
            1 -> {
                calendar.set(Calendar.DATE, currentDay + 1)
                updateCurrentValue(calendar.timeInMillis)
            }
            2 -> datePicker.show()
        }
    }
}