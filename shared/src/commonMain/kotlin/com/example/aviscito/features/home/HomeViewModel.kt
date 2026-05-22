package com.example.aviscito.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviscito.data.PillEntity
import com.example.aviscito.data.PillRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class HomeUiState(
    val pendingPills: List<PillEntity> = emptyList(),
    val todayBit: Int = 0,
    val isLoading: Boolean = false
)

class HomeViewModel(private val repository: PillRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState(todayBit = computeTodayBit()))
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init { loadPendingPills() }

    fun markAsTaken(id: Long) {
        viewModelScope.launch { repository.markAsTaken(id) }
    }

    private fun loadPendingPills() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getPendingPills().collect { pills ->
                _state.update { it.copy(pendingPills = pills, isLoading = false) }
            }
        }
    }

    private fun computeTodayBit(): Int {
        val dayOfWeek = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek
        return 1 shl dayOfWeek.ordinal
    }
}
