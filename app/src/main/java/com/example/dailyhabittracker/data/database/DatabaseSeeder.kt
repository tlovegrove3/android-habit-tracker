package com.example.dailyhabittracker.data.database

import android.content.Context
import com.example.dailyhabittracker.data.model.Habit
import com.example.dailyhabittracker.data.repository.HabitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

/**
 * Helper class to seed the database with initial data
 */
object DatabaseSeeder {

    /**
     * Seeds the database with sample habits and completion history
     * @param context The application context
     */
    fun seedDatabase(context: Context) {
        val repository = HabitRepository(context)

        // Create sample habits with completion history
        val sampleHabits = createSampleHabits()

        // Insert sample habits into the database
        CoroutineScope(Dispatchers.IO).launch {
            sampleHabits.forEach { habit ->
                repository.insertHabit(habit)
            }
        }
    }

    /**
     * Creates a list of sample habits with completion history
     */
    private fun createSampleHabits(): List<Habit> {
        val today = LocalDate.now()
        val habits = mutableListOf<Habit>()

        // Habit 1: Morning Meditation (good streak)
        val meditationDates = mutableListOf<LocalDate>()
        // Last 7 days completed
        for (i in 0..6) {
            meditationDates.add(today.minusDays(i.toLong()))
        }
        // Also completed some days earlier
        meditationDates.add(today.minusDays(10))
        meditationDates.add(today.minusDays(12))
        meditationDates.add(today.minusDays(15))

        habits.add(
            Habit(
                name = "Morning Meditation",
                description = "10 minutes of mindfulness meditation",
                isCompletedToday = true,
                completionDates = meditationDates.joinToString(",") { it.toString() }
            )
        )

        // Habit 2: Read 20 Pages (partial completion)
        val readingDates = mutableListOf<LocalDate>()
        // Completed yesterday, 3 days ago, and 5 days ago
        readingDates.add(today.minusDays(1))
        readingDates.add(today.minusDays(3))
        readingDates.add(today.minusDays(5))
        // And some dates last week
        readingDates.add(today.minusDays(8))
        readingDates.add(today.minusDays(9))

        habits.add(
            Habit(
                name = "Read 20 Pages",
                description = "Read at least 20 pages of any book",
                isCompletedToday = false,
                completionDates = readingDates.joinToString(",") { it.toString() }
            )
        )

        // Habit 3: Drink Water (perfect streak)
        val waterDates = mutableListOf<LocalDate>()
        // Completed every day for the last 14 days
        for (i in 0..13) {
            waterDates.add(today.minusDays(i.toLong()))
        }

        habits.add(
            Habit(
                name = "Drink Water",
                description = "8 glasses of water throughout the day",
                isCompletedToday = true,
                completionDates = waterDates.joinToString(",") { it.toString() }
            )
        )

        // Habit 4: Exercise (No streak, just started)
        val exerciseDates = mutableListOf<LocalDate>()
        exerciseDates.add(today) // Just started today

        habits.add(
            Habit(
                name = "Exercise",
                description = "30 minutes of physical activity",
                isCompletedToday = true,
                completionDates = exerciseDates.joinToString(",") { it.toString() }
            )
        )

        // Habit 5: No Screen Time After 10PM (no completions yet)
        habits.add(
            Habit(
                name = "No Screen Time After 10PM",
                description = "Turn off all screens at least an hour before bed",
                isCompletedToday = false,
                completionDates = ""
            )
        )

        return habits
    }
}