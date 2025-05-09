package com.example.dailyhabittracker.ui.screens.help

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help") },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // About the App Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "About Daily Habit Tracker",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Daily Habit Tracker helps you build better habits by tracking your daily progress. Consistency is key to forming lasting habits, and this app provides a simple interface to mark your daily completions and monitor your streaks.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // How to Use Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "How to Use the App",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "1. Add habits you want to track from the main screen",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "2. Check off habits as you complete them each day",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "3. Tap on a habit to view detailed information and history",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "4. Customize your experience in the Preferences section",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Preferences Explanation Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Preferences Guide",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Reminder Time
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Daily Reminder Time",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Set when you'd like to receive reminder notifications to complete your habits. Default is 9:00 AM.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Streak Notifications
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Streak Notifications",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Enable notifications when you reach habit milestones (e.g., 7-day streak, 30-day streak).",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Habits to Show
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Habits on Dashboard",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Choose how many habits to display on the main screen (5-20). This helps keep your dashboard focused.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Theme
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "App Theme",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Switch between light and dark mode. The theme is applied immediately without restarting the app.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // Tips Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Tips for Success",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "• Start with 2-3 habits to avoid overwhelming yourself",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "• Be specific with your habit descriptions",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "• Aim for consistency rather than perfection",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "• Review your progress regularly to stay motivated",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}