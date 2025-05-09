package com.example.dailyhabittracker.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.dailyhabittracker.ui.components.AddHabitDialog
import com.example.dailyhabittracker.ui.components.HabitItem
import com.example.dailyhabittracker.viewmodel.HabitListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    // Get application context for the ViewModel
    val context = LocalContext.current.applicationContext

    // Create ViewModel with the proper factory
    val viewModel: HabitListViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory(context as android.app.Application)
    )

    val habits by viewModel.habits.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Habit")
            }
        },
        topBar = {
            MediumTopAppBar(
                title = { Text(
                    "Daily Habit Tracker",
                    color = MaterialTheme.colorScheme.onSurface
                ) },
                actions = {
                    IconButton(onClick = { navController.navigate("preferences") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = { navController.navigate("help") }) {
                        Icon(Icons.Default.Info, contentDescription = "Help")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(habits, key = { it.id }) { habit ->
                HabitItem(
                    habit = habit,
                    onCheckedChange = { _ ->
                        viewModel.toggleHabitCompletion(habit)
                    },
                    onClick = { navController.navigate("details/${habit.id}") }
                )
            }

            item {
                Button(
                    onClick = { showAddDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text("Add New Habit")
                }
            }
        }

        if (showAddDialog) {
            AddHabitDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, description ->
                    viewModel.addHabit(name, description)
                    showAddDialog = false
                }
            )
        }
    }
}