package com.example.aviscito.data

fun timeToMinutes(hour: Int, minute: Int, isAM: Boolean): Int {
    val h = when {
        hour == 12 && isAM -> 0
        hour == 12 && !isAM -> 12
        !isAM -> hour + 12
        else -> hour
    }
    return h * 60 + minute
}

fun Int.toDisplayTime(): String {
    val hours = this / 60
    val minutes = this % 60
    val isPM = hours >= 12
    val displayHour = when {
        hours == 0 -> 12
        hours > 12 -> hours - 12
        else -> hours
    }
    val amPm = if (isPM) "PM" else "AM"
    return "${displayHour}:${minutes.toString().padStart(2, '0')} $amPm"
}
