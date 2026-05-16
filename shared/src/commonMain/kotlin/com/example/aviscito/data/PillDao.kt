package com.example.aviscito.data

import androidx.room.Dao
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

    @Query("SELECT * FROM pills WHERE takenAt IS NULL ORDER BY takenAt ASC")
    fun getPendingPills(): Flow<List<PillEntity>>
}