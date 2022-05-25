package me.dannly.mirainikki.presentation.note_add.components

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun noteTimePicker(
    currentMillis: Long,
    onPick: (Long) -> Unit
): TimePickerDialog {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = currentMillis
    }
    val currentMinute = calendar.get(Calendar.MINUTE)
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    return TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay, minute ->
            onPick(Calendar.getInstance().apply {
                timeInMillis = currentMillis
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }.timeInMillis)
        }, currentHour, currentMinute, Locale.getDefault() != Locale.US
    )
}