package me.dannly.mirainikki.presentation.note_add.components

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.mirainikki.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotePickTimeDialog(
    from: Long,
    currentlySelectedDateType: Int,
    currentValue: Long?,
    timePickerDialog: TimePickerDialog,
    updateCurrentValue: (Long) -> Unit
) {
    val timeValues =
        setTimeValues(current = from)
    NoteDialogDropdownMenu(
        enabled = {
            val value =
                timeValues[timeValues.keys.first { id -> stringResource(id = id) == it }]!!
            if (value == -1L)
                return@NoteDialogDropdownMenu true
            if (currentlySelectedDateType == 0) {
                value > System.currentTimeMillis()
            } else {
                value > from
            }
        },
        items = timeValues.keys.toList().map { stringResource(id = it) },
        selectedText = if (currentValue != null) SimpleDateFormat(
            "hh:mm aa",
            Locale.getDefault()
        ).format(
            Date(currentValue)
        ) else "-"
    ) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis =
                setTimeValues(currentValue ?: System.currentTimeMillis()).values.toList()[it]
            if (it != 3)
                set(Calendar.MINUTE, 0)
        }
        updateCurrentValue(calendar.timeInMillis)
        if (it == 3)
            timePickerDialog.show()
    }
}

private val rawTimeValues = mapOf(
    R.string.morning to 9,
    R.string.afternoon to 13,
    R.string.evening to 18,
    R.string.custom_time to -1
)

private fun setTimeValues(current: Long): Map<Int, Long> {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = current
    }
    val map = mutableMapOf<Int, Long>()
    val values = rawTimeValues.keys.toList()
    for (i in values.indices) {
        val id = values[i]
        map[id] = if (i != 3) calendar.apply {
            calendar.set(Calendar.HOUR_OF_DAY, rawTimeValues[id]!!)
        }.timeInMillis else -1L
    }
    return map
}