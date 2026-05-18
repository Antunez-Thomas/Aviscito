package com.example.aviscito.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviscito.data.PillEntity
import com.example.aviscito.data.PillRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PillViewModel(
    private val repo: PillRepository
): ViewModel() {
    val pills: StateFlow<List<PillEntity>> = repo.getAllPills()
       .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun addPill(name: String, frequency: String, time: String) {
        viewModelScope.launch { repo.addPill(name, frequency, time) }
    }
    fun markAsTaken(id: Long) {
        viewModelScope.launch { repo.markAsTaken(id) }
    }
    fun markAsNotTaken(id: Long) {
        viewModelScope.launch { repo.markAsNotTaken(id) }
    }
}