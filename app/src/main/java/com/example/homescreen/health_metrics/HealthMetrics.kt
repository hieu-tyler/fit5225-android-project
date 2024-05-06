package com.example.homescreen.health_metrics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

@Composable
fun HealthMetricsDashboard(userHealthMetricsNewest: UserHealthMetrics) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        HealthMetricCard(label = "Weight", value = "${userHealthMetricsNewest.weight} kg")
        HealthMetricCard(label = "BMI", value = "Calculating BMI...")
        HealthMetricCard(label = "Waist", value = "${userHealthMetricsNewest.waist} cm")
        HealthMetricCard(label = "Systolic BP", value = "${userHealthMetricsNewest.systolicBP} mmHg")
        HealthMetricCard(label = "Diastolic BP", value = "${userHealthMetricsNewest.diastolicBP} mmHg")
    }
}

@Composable
fun HealthMetricCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

@Composable
fun UserHealthDashboard(
    stepsTaken: Int,
    actualExerciseFreq: Int,
    actualExerciseTime: Int,
    userHealthMetricsNewest: UserHealthMetrics,
    navController: NavController
) {
    var bmi by remember { mutableStateOf<String?>(null) }

    // Helper function to calculate BMI
    fun calculateBMI(weight: Double, height: Double): Double {
        if (height == 0.0) return 0.0
        val heightInMeters = height / 100  // Convert height from cm to meters
        return weight / (heightInMeters * heightInMeters)
    }

    // Suspend function to fetch latest weight and height
    suspend fun fetchLatestWeightAndHeight(): Pair<Double, Double>? {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return null

        val weightSnapshot = db.collection("users").document(userId).collection("HealthRecords")
            .whereEqualTo("recordType", "Weight")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
        val latestWeight = weightSnapshot.documents.firstOrNull()?.getDouble("value") ?: return null

        val heightSnapshot = db.collection("users").document(userId).collection("HealthRecords")
            .whereEqualTo("recordType", "Height")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
        val latestHeight = heightSnapshot.documents.firstOrNull()?.getDouble("value") ?: return null

        return Pair(latestWeight, latestHeight)
    }

    // Fetch and calculate BMI when Composable enters the composition
    LaunchedEffect(true) {
        val (weight, height) = fetchLatestWeightAndHeight() ?: return@LaunchedEffect
        bmi = calculateBMI(weight, height).toString()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "My Health Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )
//        // Top Card 1 - Exercise goal
//        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
//            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//                val exerciseProgress = (actualExerciseFreq * actualExerciseTime).toFloat() / (exerciseFreq * exerciseTime)
//                val exerciseProgressPercent = String.format("%.1f", exerciseProgress * 100)
//                Text(text = "You've completed $exerciseProgressPercent% exercise!",
//                    style = MaterialTheme.typography.titleLarge)
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween) {
//                    CircularProgressIndicator(
//                        progress = exerciseProgress,
//                        color = MaterialTheme.colorScheme.primary,
//                        strokeWidth = 8.dp,
//                        modifier = Modifier.padding(16.dp).size(80.dp)
//                    )
//                    Column(modifier = Modifier.fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(text = "Goal: $exerciseType for $exerciseTime min, \n$exerciseFreq times/week")
//                        Spacer(modifier = Modifier.height(8.dp))
//                        OutlinedButton(onClick = { navController.navigate("ExerciseGoalSettingsScreen") }) {
//                            Text(
//                                text = "Change your exercise goal",
//                                style = MaterialTheme.typography.titleSmall
//                            )
//                        }
//                    }
//                }
//            }
//        }
//        // Top Card 2 - Steps
//        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
//            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//                val stepProgress = stepsTaken.toFloat() / 10000
//                Text(text = "You've walked $stepsTaken steps today!",
//                    style = MaterialTheme.typography.titleLarge)
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween) {
//                    CircularProgressIndicator(
//                        progress = stepProgress,
//                        color = MaterialTheme.colorScheme.primary,
//                        strokeWidth = 8.dp,
//                        modifier = Modifier.padding(16.dp).size(80.dp)
//                    )
//                    Column(modifier = Modifier.fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(text = "Goal: $stepsGoal steps/day")
//                        Spacer(modifier = Modifier.height(8.dp))
//                        OutlinedButton(onClick = { navController.navigate("StepsGoalSettingsScreen") }) {
//                            Text(
//                                text = "Change your steps goal",
//                                style = MaterialTheme.typography.titleSmall
//                            )
//                        }
//                    }
//                }
//            }
//        }
        Button(
            onClick = { navController.navigate("HealthMetricsSettingsScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create New Health Record")
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Bottom Cards - Horizontal Arrangement
        HealthMetricsDashboard(userHealthMetricsNewest = userHealthMetricsNewest)
    }
}
