package com.example.aviscito.features.pilltracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviscito.data.PillRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PillViewModel(private val repository: PillRepository): ViewModel() {
    private val _state = MutableStateFlow(PillUiState())
    val state: StateFlow<PillUiState> = _state.asStateFlow()

    init { loadPills() }

    fun handleEvent(event: PillUIEvent) {
        when (event) {
            is PillUIEvent.AddPill -> addPill(event.name, event.frequency, event.time)
            is PillUIEvent.MarkAsTaken -> markAsTaken(event.id)
            is PillUIEvent.MarkAsNotTaken -> markAsNotTaken(event.id)
            is PillUIEvent.Refresh -> loadPills()
        }
    }

    private fun addPill(name: String, frequency: String, time: Int) {
        viewModelScope.launch {
            repository.addPill(name, frequency, time)
        }
    }

    private fun markAsTaken(id: Long) {
        viewModelScope.launch {
            repository.markAsTaken(id)
        }
    }

    private fun markAsNotTaken(id: Long) {
        viewModelScope.launch {
            repository.markAsNotTaken(id)
        }
    }

    private fun loadPills() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getAllPills().collect { pills ->
                _state.update { it.copy(pills = pills, isLoading = false) }
            }
        }
    }
}