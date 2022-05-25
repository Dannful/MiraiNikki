package me.dannly.mirainikki.data.mapper

import me.dannly.mirainikki.data.entity.NoteEntity
import me.dannly.mirainikki.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        text = text,
        attachments = attachments,
        from = from,
        to = to
    )
}