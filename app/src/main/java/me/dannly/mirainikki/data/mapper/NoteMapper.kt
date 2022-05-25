package me.dannly.mirainikki.data.mapper

import me.dannly.mirainikki.data.entity.NoteEntity
import me.dannly.mirainikki.domain.model.Note

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        text = text,
        attachments = attachments,
        from = from,
        to = to
    )
}