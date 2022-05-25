package me.dannly.mirainikki.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.dannly.mirainikki.data.dao.NoteDao
import me.dannly.mirainikki.data.mapper.toNote
import me.dannly.mirainikki.data.mapper.toNoteEntity
import me.dannly.mirainikki.domain.model.Note
import me.dannly.mirainikki.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun observeAllNotes(current: Long): Flow<List<Note>> {
        return noteDao.observeAllNotes(current).map { list -> list.map { it.toNote() } }
    }

    override suspend fun getById(id: Int): Note? {
        return noteDao.getById(id)?.toNote()
    }

    override suspend fun insert(note: Note) {
        noteDao.insert(note.toNoteEntity())
    }

    override suspend fun delete(vararg notes: Note) {
        noteDao.delete(*notes.map { it.toNoteEntity() }.toTypedArray())
    }

    override suspend fun clearPastNotes(current: Long) {
        noteDao.clearPastNotes(current)
    }
}