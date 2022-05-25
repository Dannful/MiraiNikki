package me.dannly.mirainikki.presentation.note_add

import android.net.Uri
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi

@OptIn(ExperimentalMaterialApi::class)
sealed class NoteAddEvent {

    sealed class UiEvent {

        data class SetDrawerValue(val bottomDrawerValue: BottomDrawerValue): UiEvent()
        object LaunchContract: UiEvent()
    }

    data class SetText(val text: String) : NoteAddEvent()
    data class SetTitle(val title: String) : NoteAddEvent()
    data class AddAttachment(val uri: Uri) : NoteAddEvent()
    data class RemoveAttachment(val uri: Uri): NoteAddEvent()
    data class SetFrom(val from: Long) : NoteAddEvent()
    data class SetTo(val to: Long?) : NoteAddEvent()
    object Save : NoteAddEvent()
}
