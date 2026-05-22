package com.example.aviscito.data

import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock

class PillRepository(private val dao: PillDao) {
    fun getPendingPills(): Flow<List<PillEntity>> = dao.getPendingPills()

    suspend fun getPIllById(id: Long): PillEntity? = dao.getPillById(id)

    suspend fun addPill(name: String, daysOfWeek: Int, time: Int) {
       dao.insert(PillEntity(name = name, daysOfWeek = daysOfWeek, time = time))
    }
    suspend fun markAsTaken(id: Long) {
        val now = Clock.System.now().toEpochMilliseconds()
        val pill = dao.getPillById(id) ?: return
        dao.markAsTaken(id, now)
        dao.insertHistory(
            PillHistoryEntry(
                pillId = id,
                pillName = pill.name,
                takenAtMillis = now,
                takenAtEpochDay = now.millisToEpochDay(),
                scheduledTime = pill.time
            )
        )
    }
    suspend fun markAsNotTaken(id: Long) {
        dao.markAsNotTaken(id)
        val latest = dao.getLatestHistoryForPill(id)
        if (latest != null) {
            dao.deleteHistoryEntry(latest)
        }
    }
    fun getAllPills(): Flow<List<PillEntity>> = dao.getAllPills()

    fun getTakenDaysInRange(startEpochDay: Long, endEpochDay: Long): Flow<List<Long>> =
        dao.getTakenDaysInRange(startEpochDay, endEpochDay)

    fun getHistoryForDay(epochDay: Long): Flow<List<PillHistoryEntry>> =
        dao.getHistoryForDay(epochDay)
}