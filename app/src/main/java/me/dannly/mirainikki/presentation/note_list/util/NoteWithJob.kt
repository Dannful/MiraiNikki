package me.dannly.mirainikki.presentation.note_list.util

import kotlinx.coroutines.Job
import me.dannly.mirainikki.domain.model.Note

data class NoteWithJob(
    val note: Note,
    val job: Job
)
