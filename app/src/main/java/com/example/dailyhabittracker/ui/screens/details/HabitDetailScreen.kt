package com.example.dailyhabittracker.ui.screens.details

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.dailyhabittracker.ui.components.HabitHistoryCalendar
import com.example.dailyhabittracker.util.HabitHistoryHelper
import com.example.dailyhabittracker.viewmodel.HabitDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
    habitId: String?,
    navController: NavHostController
) {
    // Get application context for the ViewModel
    val context = LocalContext.current.applicationContext

    // Create ViewModel with the proper factory
    val viewModel: HabitDetailViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory(context as android.app.Application)
    )

    // Collect state
    val habit by viewModel.habit.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val historyDays by viewModel.historyDays.collectAsState()
    val completionDates by viewModel.completionDates.collectAsState()
    val currentStreak by viewModel.currentStreak.collectAsState()
    val longestStreak by viewModel.longestStreak.collectAsState()

    // Load habit when the screen is shown
    LaunchedEffect(habitId) {
        habitId?.toIntOrNull()?.let { id ->
            viewModel.loadHabit(id)
        }
    }

    // Get dates for the calendar
    val datesList = viewModel.getLastNDays(historyDays)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            habit == null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Habit not found")
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Go Back")
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Habit title
                    Text(
                        text = habit!!.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    // Habit description
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Description & Goal",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = habit!!.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Today's status
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Today's Status",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Switch(
                                checked = habit!!.isCompletedToday,
                                onCheckedChange = { completed ->
                                    viewModel.toggleCompletion(completed)
                                }
                            )
                        }
                    }

                    // Completion history
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Completion History",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                // Period selector
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    FilterChip(
                                        selected = historyDays == 7,
                                        onClick = { viewModel.setHistoryDaysToShow(7) },
                                        label = { Text("7D") }
                                    )
                                    FilterChip(
                                        selected = historyDays == 14,
                                        onClick = { viewModel.setHistoryDaysToShow(14) },
                                        label = { Text("14D") }
                                    )
                                    FilterChip(
                                        selected = historyDays == 30,
                                        onClick = { viewModel.setHistoryDaysToShow(30) },
                                        label = { Text("30D") }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            HabitHistoryCalendar(
                                habit = habit!!,
                                dates = datesList,
                                completedDates = completionDates,
                                currentStreak = currentStreak,
                                longestStreak = longestStreak,
                                isDateCompleted = { date ->
                                    HabitHistoryHelper.isCompletedOn(habit!!, date)
                                }
                            )
                        }
                    }

                    // Motivational quote
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Motivation",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Habits are the compound interest of self-improvement. The same way money multiplies through compound interest, the effects of your habits multiply as you repeat them.",
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic
                            )
                            Text(
                                text = "— James Clear",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}