package com.example.homescreen.health_metrics

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.Routes
import java.util.Date

@RequiresApi(64)
@Composable
fun HealthScreen() {
    val subNavController = rememberNavController()

    Column {
        val sampleUserHealthMetrics = UserHealthMetrics(
            userId = 1,
            entryDate = Date(),
            weight = 60F,
            height = 170F,
            bmi = 20F,
            waist = 105F,
            systolicBP = 120F,
            diastolicBP = 80F,
            exerciseType = "running",
            exerciseFreq = 3,
            exerciseTime = 30,
            exerciseNote = "",
            stepsGoal = 10000
        )
        // Nested NavHost
        NavHost(navController = subNavController, startDestination = Routes.HealthMetrics.value) {
            composable(Routes.HealthMetrics.value) {
                UserHealthDashboard(stepsTaken = 5500, actualExerciseFreq = 2,
                    actualExerciseTime = 30, userHealthMetricsNewest = sampleUserHealthMetrics, subNavController)
            }
            composable("HealthMetricsSettingsScreen") {
                HealthMetricsSettingsScreen(
                    userHealthMetrics = sampleUserHealthMetrics,
                    onSaveMetrics = {},
                    subNavController
                )
            }
            composable("ExerciseGoalSettingsScreen") {
                ExerciseGoalSettingsScreen(
                    userHealthMetrics = sampleUserHealthMetrics,
                    onSaveMetrics = {},
                    subNavController
                )
            }
            composable("StepsGoalSettingsScreen") {
                StepsGoalSettingsScreen(
                    userHealthMetrics = sampleUserHealthMetrics,
                    onSaveMetrics = {},
                    subNavController
                )
            }
        }
    }
}