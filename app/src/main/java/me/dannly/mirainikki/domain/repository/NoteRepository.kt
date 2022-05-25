package me.dannly.mirainikki.domain.repository

import kotlinx.coroutines.flow.Flow
import me.dannly.mirainikki.domain.model.Note

interface NoteRepository {

    fun observeAllNotes(current: Long): Flow<List<Note>>

    suspend fun getById(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(vararg notes: Note)

    suspend fun clearPastNotes(current: Long)
}