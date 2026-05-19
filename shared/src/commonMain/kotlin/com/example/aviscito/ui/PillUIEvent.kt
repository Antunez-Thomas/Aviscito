package com.example.aviscito.ui

sealed interface PillUIEvent {
    data class AddPill(val name: String, val frequency: String, val time: String) : PillUIEvent
    data class MarkAsTaken(val id: Long) : PillUIEvent
    data class MarkAsNotTaken(val id: Long) : PillUIEvent
    data object Refresh : PillUIEvent
}