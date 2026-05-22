package com.example.aviscito.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aviscito.data.AndroidApp

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    return Room.databaseBuilder(
        AndroidApp.context,
        AppDatabase::class.java,
        "pills.db"
    ).fallbackToDestructiveMigration(true)
}
