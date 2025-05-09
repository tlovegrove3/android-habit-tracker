package com.example.dailyhabittracker.data.converters

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeParseException

class DateConverters {
    @TypeConverter
    fun fromString(value: String?): List<LocalDate> {
        if (value.isNullOrEmpty()) return emptyList()

        return try {
            value.split(",").mapNotNull { dateStr ->
                try {
                    LocalDate.parse(dateStr.trim())
                } catch (e: DateTimeParseException) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromList(dates: List<LocalDate>?): String {
        if (dates.isNullOrEmpty()) return ""

        return dates.joinToString(",") { it.toString() }
    }
}