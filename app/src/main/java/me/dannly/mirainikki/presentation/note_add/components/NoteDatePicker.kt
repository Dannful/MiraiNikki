package me.dannly.mirainikki.presentation.note_add.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun noteDatePicker(
    currentMillis: Long,
    onPick: (Long) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = currentMillis
    }
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    return DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            onPick(
                Calendar.getInstance()
                    .apply {
                        timeInMillis = currentMillis
                        set(year, month, day)
                    }.timeInMillis
            )
        }, currentYear, currentMonth, currentDay
    )
}