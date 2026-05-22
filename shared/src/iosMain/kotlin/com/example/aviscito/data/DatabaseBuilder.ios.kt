package com.example.aviscito.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    return Room.databaseBuilder<AppDatabase>(name = "pills.db")
        .setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigration(true)
}