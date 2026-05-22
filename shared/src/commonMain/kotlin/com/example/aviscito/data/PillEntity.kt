package com.example.aviscito.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pills")
data class PillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val frequency: String,
    val time: Int,
    val takenAt: Long? = null
)