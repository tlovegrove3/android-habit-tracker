package com.example.dailyhabittracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailyhabittracker.ui.screens.details.HabitDetailScreen
import com.example.dailyhabittracker.ui.screens.help.HelpScreen
import com.example.dailyhabittracker.ui.screens.main.HomeScreen
import com.example.dailyhabittracker.ui.screens.preferences.PreferencesScreen
import com.example.dailyhabittracker.ui.theme.DailyHabitTrackerTheme
import com.example.dailyhabittracker.viewmodel.PreferencesViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val preferencesViewModel = PreferencesViewModel()
            preferencesViewModel.getPreferences(this@MainActivity)
                .collect { preferences ->
        setContent {


            DailyHabitTrackerTheme(darkTheme = preferences.isDarkTheme) {
                Surface {
                    val navController = rememberNavController()

                    Scaffold { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = "home",
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable("home") {
                                HomeScreen(navController)
                            }
                            composable("details/{habitId}") { backStackEntry ->
                                val habitId = backStackEntry.arguments?.getString("habitId")
                                HabitDetailScreen(habitId, navController)
                            }
                            composable("preferences") {
                                PreferencesScreen(navController)
                            }
                            composable("help") {
                                HelpScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}}}