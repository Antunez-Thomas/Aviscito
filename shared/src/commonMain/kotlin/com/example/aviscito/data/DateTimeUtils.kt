package com.example.aviscito.data

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant as KotlinInstant

val dayNames = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

fun daysOfWeekToText(daysOfWeek: Int): String {
    val selected = dayNames.filterIndexed { i, _ -> (daysOfWeek and (1 shl i)) != 0 }
    return when {
        selected.isEmpty() -> "Never"
        selected.size == 7 -> "Every day"
        selected.size == 5 && selected == dayNames.take(5) -> "Weekdays"
        selected.size == 2 && selected == dayNames.takeLast(2) -> "Weekends"
        else -> selected.joinToString(", ")
    }
}

fun Int.toDisplayTime(): String {
    val hours = this / 60
    val minutes = this % 60
    val period = if (hours < 12) "AM" else "PM"
    val displayHour = when {
        hours == 0 -> 12
        hours > 12 -> hours - 12
        else -> hours
    }
    return "${displayHour}:${minutes.toString().padStart(2, '0')} $period"
}

fun Long.millisToEpochDay(): Long {
    return KotlinInstant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
        .toEpochDays()
}
