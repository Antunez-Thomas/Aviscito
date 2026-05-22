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
        dao.markAsTaken(id, Clock.System.now().toEpochMilliseconds())
    }
    suspend fun markAsNotTaken(id: Long) {
        dao.markAsNotTaken(id)
    }
    fun getAllPills(): Flow<List<PillEntity>> = dao.getAllPills()
}