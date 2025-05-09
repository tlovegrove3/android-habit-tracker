package com.example.dailyhabittracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dailyhabittracker.data.converters.DateConverters
import com.example.dailyhabittracker.data.dao.HabitDao
import com.example.dailyhabittracker.data.model.Habit

@Database(entities = [Habit::class], version = 2, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                )
                    .fallbackToDestructiveMigration(true)
                    // Add a callback to seed the database when created
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Seed the database after creation
                            DatabaseSeeder.seedDatabase(context.applicationContext)
                        }

                        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                            super.onDestructiveMigration(db)
                            // Re-seed the database after destructive migration
                            DatabaseSeeder.seedDatabase(context.applicationContext)
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}