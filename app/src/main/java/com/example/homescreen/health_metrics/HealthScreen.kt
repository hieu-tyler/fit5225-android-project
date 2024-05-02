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
        // Nested NavHost
        NavHost(navController = subNavController, startDestination = Routes.HealthMetrics.value) {
            composable(Routes.HealthMetrics.value) {
                val sampleMetrics = UserHealthMetrics(
                    userId = 1,
                    entryDate = Date(),
                    weight = 70f,
                    height = 175f,
                    bmi = 22.9f,
                    waist = 87f,
                    exerciseType = "running",
                    exerciseFreq = 3,
                    exerciseTime = 30,
                    exerciseNote = "",
                    systolicBP = 160f,
                    diastolicBP = 95f
                )
                UserHealthDashboard(stepsTaken = 5500, actualExerciseFreq = 2,
                    actualExerciseTime = 30, userHealthMetricsNewest = sampleMetrics, subNavController)
            }
            composable("HealthMetricsSettingsScreen") {
                val sampleUserHealthMetrics = UserHealthMetrics(
                    userId = 1,
                    entryDate = Date(),
                    weight = 60F,
                    height = 170F,
                    bmi = 20F,
                    waist = 100F,
                    exerciseType = "running",
                    exerciseFreq = 3,
                    exerciseTime = 30,
                    exerciseNote = "",
                    systolicBP = 120F,
                    diastolicBP = 80F
                )
                HealthMetricsSettingsScreen(
                    userHealthMetrics = sampleUserHealthMetrics,
                    onSaveMetrics = {},
                    subNavController
                )
            }
        }
    }
}