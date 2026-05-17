package com.example.aviscito

import android.app.Application
import com.example.aviscito.data.AndroidApp

class PillTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidApp.context = this
    }
}