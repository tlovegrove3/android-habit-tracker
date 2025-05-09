package com.example.dailyhabittracker.ui.screens.preferences

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dailyhabittracker.data.preferences.AppPreferences
import com.example.dailyhabittracker.ui.components.PreferenceCard
import com.example.dailyhabittracker.ui.components.TimePickerDialog
import com.example.dailyhabittracker.viewmodel.PreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val preferencesViewModel = remember { PreferencesViewModel() }
    val preferences by preferencesViewModel.getPreferences(context).collectAsState(initial = AppPreferences())
    var showTimePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preferences") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Daily Reminder Time
            PreferenceCard(
                title = "Daily Reminder Time",
                description = "Set when you'd like to be reminded to complete habits"
            ) {
                if (showTimePicker) {
                    TimePickerDialog(
                        onConfirm = { time ->
                            preferencesViewModel.updateReminderTime(context, time)
                            showTimePicker = false
                        },
                        onDismiss = { showTimePicker = false },
                        initialTime = preferences.reminderTime
                    )
                }

                OutlinedButton(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(preferences.reminderTime)
                }
            }

            // Streak Notifications
            PreferenceCard(
                title = "Streak Notifications",
                description = "Get notified when you reach habit milestones"
            ) {
                Switch(
                    checked = preferences.enableStreakNotifications,
                    onCheckedChange = {
                        preferencesViewModel.updateStreakNotifications(context, it)
                    }
                )
            }

            // Number of Habits to Show
            PreferenceCard(
                title = "Habits on Dashboard",
                description = "Number of habits to display on the main screen"
            ) {
                Slider(
                    value = preferences.habitsToShow.toFloat(),
                    onValueChange = { value ->
                        preferencesViewModel.updateHabitsToShow(context, value.toInt())
                    },
                    valueRange = 5f..20f,
                    steps = 14,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "${preferences.habitsToShow} habits",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            // Theme Selection
            PreferenceCard(
                title = "App Theme",
                description = "Choose between light and dark theme"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        onClick = { preferencesViewModel.updateTheme(context, false) },
                        label = { Text("Light") },
                        selected = !preferences.isDarkTheme,
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        onClick = { preferencesViewModel.updateTheme(context, true) },
                        label = { Text("Dark") },
                        selected = preferences.isDarkTheme,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}