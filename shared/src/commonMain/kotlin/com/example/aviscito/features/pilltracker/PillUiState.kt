package com.example.aviscito.features.pilltracker

import com.example.aviscito.data.PillEntity

// empty state, represents the initial state before any data loads
data class PillUiState(
    val pills: List<PillEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)