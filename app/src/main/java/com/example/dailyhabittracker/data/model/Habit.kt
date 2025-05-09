package com.example.dailyhabittracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val isCompletedToday: Boolean = false,
    val completionDates: String = "",
    val createdTimestamp: Long = System.currentTimeMillis()
)