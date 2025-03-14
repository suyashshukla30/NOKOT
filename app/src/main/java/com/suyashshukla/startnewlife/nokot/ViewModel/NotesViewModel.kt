package com.suyashshukla.startnewlife.nokot.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suyashshukla.startnewlife.nokot.Model.Local.NotesDataClass
import com.suyashshukla.startnewlife.nokot.Model.Repository.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository): ViewModel() {

    private val _notes = MutableStateFlow<List<NotesDataClass>>(emptyList())
    val notes: StateFlow<List<NotesDataClass>> get() = _notes

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isLoading = MutableStateFlow(true) // Initially true
    val isLoading: StateFlow<Boolean> get() = _isLoading
    init {
        syncNotes() // Fetch initial data when ViewModel is created
    }

    fun syncNotes() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                notesRepository.syncNotesFromFirestore()
                _notes.value = notesRepository.getAllLocalNotes()
            } catch (e: Exception) {
                _errorMessage.value = "Error syncing notes: ${e.message}"
            } finally{
                _isLoading.value = false
            }
        }
    }
    fun saveNote(note: NotesDataClass) {
        viewModelScope.launch {
            try {
                notesRepository.saveNoteToLocal(note)
                notesRepository.saveNoteToFirestore(note)
                syncNotes() // Refresh notes
            } catch (e: Exception) {
                _errorMessage.value = "Error saving note: ${e.message}"
            }
        }
    }
    fun deleteNote(note: NotesDataClass) {
        viewModelScope.launch {
            try {
                notesRepository.deleteNoteFromLocal(note)
                notesRepository.deleteNoteFromFirestore(note.id)
                syncNotes() // Refresh notes
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting note: ${e.message}"
            }
        }
    }
}