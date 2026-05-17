package com.example.aviscito.data

import androidx.room.Room
import androidx.room.RoomDatabase

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

fun getAppDatabase(): AppDatabase = getDatabaseBuilder().build()