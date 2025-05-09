package com.example.dailyhabittracker.util

import com.example.dailyhabittracker.data.model.Habit
import org.threeten.bp.LocalDate

/**
 * Helper class for habit completion history functionality.
 * This keeps streak calculations and date parsing separate from the Habit model
 * while still providing all the necessary functionality.
 */
object HabitHistoryHelper {

    /**
     * Convert a completion dates string to a list of LocalDate objects
     */
    fun parseCompletionDates(value: String): List<LocalDate> {
        if (value.isEmpty()) return emptyList()

        return try {
            value.split(",").mapNotNull { dateStr ->
                try {
                    LocalDate.parse(dateStr.trim())
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Convert a list of LocalDate objects to a string for storage
     */
    fun dateListToString(dates: List<LocalDate>): String {
        if (dates.isEmpty()) return ""

        return dates.joinToString(",") { it.toString() }
    }

    /**
     * Add a completion date to the habit's history
     */
    fun addCompletionDate(habit: Habit, date: LocalDate): Habit {
        val currentDates = parseCompletionDates(habit.completionDates).toMutableList()

        // Don't add duplicate dates
        if (!currentDates.contains(date)) {
            currentDates.add(date)
        }

        val newCompletionDates = dateListToString(currentDates)

        return habit.copy(completionDates = newCompletionDates)
    }

    /**
     * Remove a completion date from the habit's history
     */
    fun removeCompletionDate(habit: Habit, date: LocalDate): Habit {
        val currentDates = parseCompletionDates(habit.completionDates).toMutableList()
        currentDates.remove(date)

        val newCompletionDates = dateListToString(currentDates)

        return habit.copy(completionDates = newCompletionDates)
    }

    /**
     * Check if a habit is completed on a specific date
     */
    fun isCompletedOn(habit: Habit, date: LocalDate): Boolean {
        return parseCompletionDates(habit.completionDates).contains(date)
    }

    /**
     * Calculate the current streak (consecutive days up to today)
     */
    fun getCurrentStreak(habit: Habit): Int {
        val dates = parseCompletionDates(habit.completionDates)
        if (dates.isEmpty()) return 0

        // Get today's date and sort the completion dates
        val today = LocalDate.now()
        val sortedDates = dates.sortedDescending()

        // If the most recent completion isn't today or yesterday, no current streak
        if (sortedDates.first().isBefore(today.minusDays(1))) return 0

        var currentDate = sortedDates.first()
        var streakCount = 1

        // Count consecutive days going backwards
        for (i in 1 until sortedDates.size) {
            val expectedPreviousDate = currentDate.minusDays(1)
            if (sortedDates[i] == expectedPreviousDate) {
                streakCount++
                currentDate = expectedPreviousDate
            } else {
                break
            }
        }

        return streakCount
    }

    /**
     * Calculate the longest streak (consecutive days ever)
     */
    fun getLongestStreak(habit: Habit): Int {
        val dates = parseCompletionDates(habit.completionDates)
        if (dates.isEmpty()) return 0

        val sortedDates = dates.sorted()
        var longestStreak = 1
        var currentStreak = 1
        var previousDate = sortedDates.first()

        for (i in 1 until sortedDates.size) {
            val currentDate = sortedDates[i]
            val dayDifference = currentDate.toEpochDay() - previousDate.toEpochDay()

            if (dayDifference == 1L) {
                // Consecutive day
                currentStreak++
                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak
                }
            } else if (dayDifference > 1L) {
                // Streak broken
                currentStreak = 1
            }

            previousDate = currentDate
        }

        return longestStreak
    }

    /**
     * Generate a list of LocalDate objects for the last N days
     */
    fun getLastNDays(days: Int): List<LocalDate> {
        val result = mutableListOf<LocalDate>()
        val today = LocalDate.now()

        for (i in 0 until days) {
            result.add(today.minusDays(i.toLong()))
        }

        return result.reversed() // Return in chronological order
    }
}