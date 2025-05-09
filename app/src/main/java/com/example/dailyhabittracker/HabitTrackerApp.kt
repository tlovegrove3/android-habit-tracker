package com.example.dailyhabittracker

import android.app.Application
import android.util.Log
import com.jakewharton.threetenabp.AndroidThreeTen

class HabitTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // Initialize ThreeTenABP for date/time support on older Android versions
            Log.d("HabitTracker", "Initializing AndroidThreeTen...")
            AndroidThreeTen.init(this)
            Log.d("HabitTracker", "AndroidThreeTen initialized successfully")
        } catch (e: Exception) {
            // Log error but don't crash the app
            Log.e("HabitTracker", "Error initializing AndroidThreeTen", e)
        }

        Log.d("HabitTracker", "HabitTrackerApp initialized")
    }
}