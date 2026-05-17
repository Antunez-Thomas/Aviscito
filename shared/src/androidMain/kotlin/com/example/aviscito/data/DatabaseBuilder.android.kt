package com.example.aviscito.data

import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = AndroidApp.Context
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "pills.db"
    )
}
