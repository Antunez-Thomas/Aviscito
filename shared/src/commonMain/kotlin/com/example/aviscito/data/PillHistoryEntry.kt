package com.example.aviscito.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pill_history")
data class PillHistoryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pillId: Long,
    val pillName: String,
    val takenAtMillis: Long,
    val takenAtEpochDay: Long,
    val scheduledTime: Int
)
