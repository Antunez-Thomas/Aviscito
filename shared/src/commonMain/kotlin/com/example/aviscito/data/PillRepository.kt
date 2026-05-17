package com.example.aviscito.data

import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock


class PillRepository(private val dao: PillDao) {
    fun getPendingPills(): Flow<List<PillEntity>> = dao.getPendingPills()

    suspend fun getPIllById(Id: Long): PillEntity? = dao.getPillById(Id)

    suspend fun addPill(name: String, frequency: String, time: String) {
       dao.insert(PillEntity(name = name, frequency = frequency, time = time))
    }
    suspend fun markAsTaken(id: Long) {
        dao.markAsTaken(id, Clock.System.now().toEpochMilliseconds())
    }
    suspend fun markAsNotTaken(id: Long) {
        dao.markAsNotTaken(id)
    }
}