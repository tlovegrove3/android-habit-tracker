package com.example.dailyhabittracker.data.preferences

data class AppPreferences(
    val reminderTime: String = "09:00",
    val enableStreakNotifications: Boolean = true,
    val habitsToShow: Int = 10,
    val isDarkTheme: Boolean = false
)
