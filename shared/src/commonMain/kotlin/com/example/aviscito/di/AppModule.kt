package com.example.aviscito.di

import com.example.aviscito.data.AppDatabase
import com.example.aviscito.data.PillRepository
import com.example.aviscito.data.getAppDatabase
import com.example.aviscito.features.pilltracker.PillViewModel
import org.koin.dsl.module

val appModule = module {
    single { getAppDatabase() }
    single { get<AppDatabase>().pillDao()}
    single { PillRepository(get()) }
    single { PillViewModel(get()) }
}