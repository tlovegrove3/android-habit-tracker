package com.example.dailyhabittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyhabittracker.data.model.Habit
import com.example.dailyhabittracker.data.repository.HabitRepository
import com.example.dailyhabittracker.util.HabitHistoryHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class HabitDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HabitRepository(application)

    private val _habit = MutableStateFlow<Habit?>(null)
    val habit: StateFlow<Habit?> = _habit

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // For displaying the last 7, 14, or 30 days of history
    private val _historyDays = MutableStateFlow(7)
    val historyDays: StateFlow<Int> = _historyDays

    // To store the parsed completion dates
    private val _completionDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val completionDates: StateFlow<List<LocalDate>> = _completionDates

    // Current and longest streaks
    private val _currentStreak = MutableStateFlow(0)
    val currentStreak: StateFlow<Int> = _currentStreak

    private val _longestStreak = MutableStateFlow(0)
    val longestStreak: StateFlow<Int> = _longestStreak

    fun loadHabit(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val loadedHabit = repository.getHabitById(id)
            _habit.value = loadedHabit

            if (loadedHabit != null) {
                // Parse completion dates
                updateCompletionInfo(loadedHabit)
            }

            _isLoading.value = false
        }
    }

    fun toggleCompletion(completed: Boolean) {
        viewModelScope.launch {
            _habit.value?.let { currentHabit ->
                val today = LocalDate.now()

                val updatedHabit = if (completed) {
                    // Mark as completed for today
                    val habitWithCompletion = HabitHistoryHelper.addCompletionDate(currentHabit, today)
                    habitWithCompletion.copy(isCompletedToday = true)
                } else {
                    // Unmark from today
                    val habitWithoutCompletion = HabitHistoryHelper.removeCompletionDate(currentHabit, today)
                    habitWithoutCompletion.copy(isCompletedToday = false)
                }

                repository.updateHabit(updatedHabit)
                _habit.value = updatedHabit

                // Update completion info
                updateCompletionInfo(updatedHabit)
            }
        }
    }

    fun setHistoryDaysToShow(days: Int) {
        _historyDays.value = days
    }

    fun getLastNDays(days: Int = _historyDays.value): List<LocalDate> {
        return HabitHistoryHelper.getLastNDays(days)
    }

    private fun updateCompletionInfo(habit: Habit) {
        // Update completion dates
        val dates = HabitHistoryHelper.parseCompletionDates(habit.completionDates)
        _completionDates.value = dates

        // Update streaks
        _currentStreak.value = HabitHistoryHelper.getCurrentStreak(habit)
        _longestStreak.value = HabitHistoryHelper.getLongestStreak(habit)
    }
}