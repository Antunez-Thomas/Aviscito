package com.example.aviscito.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pill: PillEntity)

    @Query("SELECT * FROM pills where id = :id")
    suspend fun getPillById(id: Long): PillEntity?

    @Query("UPDATE pills SET takenAt = :takenAt WHERE id = :id")
    suspend fun markAsTaken(id: Long, takenAt: Long)

    @Query("UPDATE pills SET takenAt = NULL WHERE id = :id")
    suspend fun markAsNotTaken(id: Long)

    @Query("SELECT * FROM pills WHERE takenAt IS NULL ORDER BY time ASC")
    fun getPendingPills(): Flow<List<PillEntity>>

    @Query("SELECT * FROM pills ORDER BY time ASC")
    fun getAllPills(): Flow<List<PillEntity>>

    @Insert
    suspend fun insertHistory(entry: PillHistoryEntry)

    @Query("SELECT * FROM pill_history WHERE pillId = :pillId ORDER BY id DESC LIMIT 1")
    suspend fun getLatestHistoryForPill(pillId: Long): PillHistoryEntry?

    @Delete
    suspend fun deleteHistoryEntry(entry: PillHistoryEntry)

    @Query("SELECT DISTINCT takenAtEpochDay FROM pill_history WHERE takenAtEpochDay BETWEEN :startEpochDay AND :endEpochDay ORDER BY takenAtEpochDay ASC")
    fun getTakenDaysInRange(startEpochDay: Long, endEpochDay: Long): Flow<List<Long>>

    @Query("SELECT * FROM pill_history WHERE takenAtEpochDay = :epochDay ORDER BY scheduledTime ASC")
    fun getHistoryForDay(epochDay: Long): Flow<List<PillHistoryEntry>>
}