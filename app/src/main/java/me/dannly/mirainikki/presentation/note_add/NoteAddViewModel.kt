package me.dannly.mirainikki.presentation.note_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import me.dannly.mirainikki.domain.model.Note
import me.dannly.mirainikki.domain.repository.DispatcherProvider
import me.dannly.mirainikki.domain.repository.NoteRepository
import me.dannly.mirainikki.navigation.Routes
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var note by mutableStateOf(
        Note(
            title = "",
            text = "",
            from = System.currentTimeMillis()
        )
    )
        private set

    private val _uiEvent = MutableSharedFlow<NoteAddEvent.UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        savedStateHandle.get<Int>(Routes.ADD_NOTE_ID_ARGUMENT)?.takeUnless { it == -1 }?.let {
            viewModelScope.launch {
                note = noteRepository.getById(it) ?: return@launch
            }
        }
    }

    fun sendUiEvent(uiEvent: NoteAddEvent.UiEvent) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiEvent.emit(uiEvent)
        }
    }

    fun onEvent(noteAddEvent: NoteAddEvent) {
        when (noteAddEvent) {
            NoteAddEvent.Save -> {
                viewModelScope.launch {
                    noteRepository.insert(
                        note
                    )
                }
            }
            is NoteAddEvent.AddAttachment -> note =
                note.copy(attachments = note.attachments + noteAddEvent.uri)
            is NoteAddEvent.SetFrom -> note = note.copy(from = noteAddEvent.from)
            is NoteAddEvent.SetText -> note = note.copy(text = noteAddEvent.text)
            is NoteAddEvent.SetTo -> note = note.copy(to = noteAddEvent.to)
            is NoteAddEvent.RemoveAttachment -> note =
                note.copy(attachments = note.attachments - noteAddEvent.uri)
            is NoteAddEvent.SetTitle -> note = note.copy(title = noteAddEvent.title)
        }
    }
}