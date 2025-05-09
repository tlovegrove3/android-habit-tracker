package com.example.dailyhabittracker.data.repository

import android.content.Context
import com.example.dailyhabittracker.data.database.HabitDatabase
import com.example.dailyhabittracker.data.model.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepository(context: Context) {
    private val habitDao = HabitDatabase.getDatabase(context).habitDao()

    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    suspend fun insertHabit(habit: Habit) {
        habitDao.insertHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    suspend fun getHabitById(id: Int): Habit? {
        return habitDao.getHabitById(id)
    }
}