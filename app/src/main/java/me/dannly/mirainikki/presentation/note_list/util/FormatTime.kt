package me.dannly.mirainikki.presentation.note_list.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.mirainikki.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun formatDate(
    epoch: Long,
    current: Long = System.currentTimeMillis()
): String {
    val oneDayInMillis = TimeUnit.DAYS.toMillis(1)
    val epochDiv = epoch / oneDayInMillis
    val currentDiv = current / oneDayInMillis
    return when {
        epochDiv == currentDiv -> stringResource(R.string.today)
        currentDiv - epochDiv == 1L -> stringResource(R.string.yesterday)
        epochDiv - currentDiv == 1L -> stringResource(R.string.tomorrow)
        else -> SimpleDateFormat("EEE, d MMM", Locale.getDefault()).format(Date(epoch))
    }
}

@Composable
fun formatDateTime(
    epoch: Long,
    current: Long = System.currentTimeMillis()
): String {
    val oneDayInMillis = TimeUnit.DAYS.toMillis(1)
    val epochDiv = epoch / oneDayInMillis
    val currentDiv = current / oneDayInMillis
    val timeFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(epoch)
    return when {
        epochDiv == currentDiv -> stringResource(R.string.today) + ", $timeFormat"
        currentDiv - epochDiv == 1L -> stringResource(R.string.yesterday) + ", $timeFormat"
        epochDiv - currentDiv == 1L -> stringResource(R.string.tomorrow) + ", $timeFormat"
        else -> SimpleDateFormat("EEE, d MMM hh:mm aa", Locale.getDefault()).format(Date(epoch))
    }
}