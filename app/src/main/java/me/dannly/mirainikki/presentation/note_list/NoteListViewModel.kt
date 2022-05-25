package me.dannly.mirainikki.presentation.note_list

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.dannly.mirainikki.domain.model.Note
import me.dannly.mirainikki.domain.repository.NoteRepository
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    var noteList by mutableStateOf<List<Note>>(emptyList())
        private set

    private var fetchNotesJob: Job? = null
    private val deletedNotes: Queue<Note> = LinkedList()

    init {
        viewModelScope.launch {
            noteRepository.clearPastNotes(System.currentTimeMillis())
            fetchNoteList()
        }
    }

    var day by mutableStateOf(System.currentTimeMillis())
        private set

    private fun cancelNoteJob() {
        val poll = deletedNotes.poll() ?: return
        viewModelScope.launch {
            noteRepository.insert(poll)
        }
    }

    fun deleteNote(
        scaffoldState: ScaffoldState,
        deletedMessage: String,
        undoMessage: String,
        noteIndex: Int
    ) {
        viewModelScope.launch {
            when (scaffoldState.snackbarHostState.showSnackbar(
                deletedMessage,
                undoMessage,
                SnackbarDuration.Short
            )) {
                SnackbarResult.Dismissed -> Unit
                SnackbarResult.ActionPerformed -> cancelNoteJob()
            }
        }
        viewModelScope.launch {
            val note = noteList[noteIndex]
            deletedNotes.offer(note)
            noteRepository.delete(note)
        }
    }

    fun previousDay() {
        day -= TimeUnit.DAYS.toMillis(1)
        fetchNoteList()
    }

    fun nextDay() {
        day += TimeUnit.DAYS.toMillis(1)
        fetchNoteList()
    }

    private fun fetchNoteList() {
        fetchNotesJob?.cancel()
        fetchNotesJob = viewModelScope.launch {
            noteRepository.observeAllNotes(day).collectLatest {
                noteList = it
            }
        }
    }
}