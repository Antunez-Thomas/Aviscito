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

data class HomeUiState(
    val pendingPills: List<PillEntity> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel(private val repository: PillRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init { loadPendingPills() }

    private fun loadPendingPills() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getPendingPills().collect { pills ->
                _state.update { it.copy(pendingPills = pills, isLoading = false) }
            }
        }
    }
}
