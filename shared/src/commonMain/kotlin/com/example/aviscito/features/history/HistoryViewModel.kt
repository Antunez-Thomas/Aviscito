package com.example.aviscito.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviscito.data.PillHistoryEntry
import com.example.aviscito.data.PillRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant as KotlinInstant

data class HistoryUiState(
    val currentYear: Int,
    val currentMonth: Int,
    val takenEpochDays: Set<Long> = emptySet(),
    val selectedEpochDay: Long? = null,
    val historyForSelectedDay: List<PillHistoryEntry> = emptyList(),
    val isLoading: Boolean = false
)

class HistoryViewModel(private val repository: PillRepository) : ViewModel() {
    private val currentDate = KotlinInstant.fromEpochMilliseconds(
        kotlin.time.Clock.System.now().toEpochMilliseconds()
    ).toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _state = MutableStateFlow(
        HistoryUiState(
            currentYear = currentDate.year,
            currentMonth = currentDate.month.ordinal + 1
        )
    )
    val state: StateFlow<HistoryUiState> = _state.asStateFlow()

    init {
        loadTakenDays()
    }

    fun nextMonth() {
        _state.update { s ->
            val (y, m) = if (s.currentMonth == 12) s.currentYear + 1 to 1
            else s.currentYear to s.currentMonth + 1
            s.copy(currentYear = y, currentMonth = m, selectedEpochDay = null)
        }
        loadTakenDays()
    }

    fun prevMonth() {
        _state.update { s ->
            val (y, m) = if (s.currentMonth == 1) s.currentYear - 1 to 12
            else s.currentYear to s.currentMonth - 1
            s.copy(currentYear = y, currentMonth = m, selectedEpochDay = null)
        }
        loadTakenDays()
    }

    fun selectDay(epochDay: Long?) {
        _state.update { it.copy(selectedEpochDay = epochDay) }
        val day = epochDay ?: return
        viewModelScope.launch {
            repository.getHistoryForDay(day).collectLatest { entries ->
                _state.update { it.copy(historyForSelectedDay = entries) }
            }
        }
    }

    private fun loadTakenDays() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val s = _state.value
            val firstOfMonth = LocalDate(s.currentYear, s.currentMonth, 1)
            val lastOfMonth = LocalDate(s.currentYear, s.currentMonth, daysInMonth(s.currentYear, s.currentMonth))
            repository.getTakenDaysInRange(firstOfMonth.toEpochDays(), lastOfMonth.toEpochDays())
                .collectLatest { days ->
                    _state.update {
                        it.copy(takenEpochDays = days.toSet(), isLoading = false)
                    }
                }
        }
    }
}

internal fun daysInMonth(year: Int, month: Int): Int = when (month) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    4, 6, 9, 11 -> 30
    2 -> if (isLeapYear(year)) 29 else 28
    else -> throw IllegalArgumentException("Invalid month: $month")
}

private fun isLeapYear(year: Int): Boolean =
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
