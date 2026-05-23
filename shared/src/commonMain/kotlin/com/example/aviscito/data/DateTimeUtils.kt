package com.example.aviscito.data

import kotlinx.datetime.DayOfWeek
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

fun Long.millisToEpochDay(): Long {
    return KotlinInstant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
        .toEpochDays()
}

fun isDaySelected(daysOfWeek: Int, day: DayOfWeek): Boolean =
    (daysOfWeek and (1 shl day.ordinal)) != 0
