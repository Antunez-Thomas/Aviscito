package com.example.aviscito.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PillEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun pillDao(): PillDao
}