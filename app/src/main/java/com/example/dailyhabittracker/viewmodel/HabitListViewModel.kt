package com.example.dailyhabittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyhabittracker.data.model.Habit
import com.example.dailyhabittracker.data.repository.HabitRepository
import com.example.dailyhabittracker.util.HabitHistoryHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class HabitListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HabitRepository(application)
    val habits: Flow<List<Habit>> = repository.allHabits

    fun addHabit(name: String, description: String) {
        viewModelScope.launch {
            val habit = Habit(
                name = name,
                description = description
            )
            repository.insertHabit(habit)
        }
    }

    fun toggleHabitCompletion(habit: Habit) {
        viewModelScope.launch {
            val today = LocalDate.now()
            val newIsCompleted = !habit.isCompletedToday

            val updatedHabit = if (newIsCompleted) {
                // Mark as completed for today
                val habitWithCompletion = HabitHistoryHelper.addCompletionDate(habit, today)
                habitWithCompletion.copy(isCompletedToday = true)
            } else {
                // Unmark from today
                val habitWithoutCompletion = HabitHistoryHelper.removeCompletionDate(habit, today)
                habitWithoutCompletion.copy(isCompletedToday = false)
            }

            repository.updateHabit(updatedHabit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }
}