package com.example.dailyhabittracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyhabittracker.data.preferences.AppPreferences
import com.example.dailyhabittracker.data.preferences.PreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PreferencesViewModel : ViewModel() {
    fun getPreferences(context: Context): Flow<AppPreferences> {
        return PreferencesDataStore.getPreferences(context)
    }

    fun updateReminderTime(context: Context, time: String) {
        viewModelScope.launch {
            PreferencesDataStore.updatePreference(context, PreferencesDataStore.REMINDER_TIME, time)
        }
    }

    fun updateStreakNotifications(context: Context, enabled: Boolean) {
        viewModelScope.launch {
            PreferencesDataStore.updatePreference(context, PreferencesDataStore.ENABLE_STREAK_NOTIFICATIONS, enabled)
        }
    }

    fun updateHabitsToShow(context: Context, count: Int) {
        viewModelScope.launch {
            PreferencesDataStore.updatePreference(context, PreferencesDataStore.HABITS_TO_SHOW, count)
        }
    }

    fun updateTheme(context: Context, isDark: Boolean) {
        viewModelScope.launch {
            PreferencesDataStore.updatePreference(context, PreferencesDataStore.IS_DARK_THEME, isDark)
        }
    }
}