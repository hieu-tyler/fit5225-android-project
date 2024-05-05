package com.example.homescreen.health_metrics

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel

@RequiresApi(64)
@Composable
fun HealthScreen(userId: String, viewModel: ViewModel) {
    val subNavController = rememberNavController()

    Column {
        // Use observeAsState with nullable type and handle nullability in the UI
        val userHealthMetrics by viewModel.getUserHealthMetrics(userId).observeAsState()

        // Nested NavHost
        NavHost(navController = subNavController, startDestination = Routes.HealthMetrics.value) {
            composable(Routes.HealthMetrics.value) {
                UserHealthDashboard(userId, stepsTaken = 5500, actualExerciseFreq = 2,
                    actualExerciseTime = 30, userHealthMetrics = userHealthMetrics, subNavController, viewModel)
            }
            composable("HealthMetricsSettingsScreen") {
                HealthMetricsSettingsScreen(
                    userId,
                    userHealthMetrics = userHealthMetrics,
                    onSaveMetrics = {},
                    navController = subNavController,
                    viewModel = viewModel
                )
            }
            composable("ExerciseGoalSettingsScreen") {
                ExerciseGoalSettingsScreen(
                    userHealthMetrics = userHealthMetrics,
                    onSaveMetrics = {},
                    navController = subNavController,
                    viewModel = viewModel
                )
            }
            composable("StepsGoalSettingsScreen") {
                StepsGoalSettingsScreen(
                    userHealthMetrics = userHealthMetrics,
                    onSaveMetrics = {},
                    navController = subNavController,
                    viewModel = viewModel
                )
            }
        }
    }
}