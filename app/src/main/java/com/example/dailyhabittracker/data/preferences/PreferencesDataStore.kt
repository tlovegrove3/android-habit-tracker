package com.example.dailyhabittracker.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

object PreferencesDataStore {
    val REMINDER_TIME = stringPreferencesKey("reminder_time")
    val ENABLE_STREAK_NOTIFICATIONS = booleanPreferencesKey("enable_streak_notifications")
    val HABITS_TO_SHOW = intPreferencesKey("habits_to_show")
    val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")

    fun getPreferences(context: Context): Flow<AppPreferences> {
        return context.dataStore.data.map { preferences ->
            AppPreferences(
                reminderTime = preferences[REMINDER_TIME] ?: "09:00",
                enableStreakNotifications = preferences[ENABLE_STREAK_NOTIFICATIONS] ?: true,
                habitsToShow = preferences[HABITS_TO_SHOW] ?: 10,
                isDarkTheme = preferences[IS_DARK_THEME] ?: false
            )
        }
    }

    suspend fun <T> updatePreference(context: Context, key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}