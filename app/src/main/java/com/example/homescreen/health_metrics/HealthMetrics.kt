package com.example.homescreen.health_metrics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs



@Composable
fun UserHealthDashboard(stepsTaken: Int, actualExerciseFreq: Int,
    actualExerciseTime: Int, userHealthMetricsNewest: UserHealthMetrics,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
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
    // Create a temporary list of data for "userHealthMetricsLast"
    var userIdLast = 1
    var entryDateLast = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("01/02/2024") ?: Date()
    var weightLast = 68F
    var heightLast = 171F
    val bmiLast = 23.3F
    var waistLast = 87F
    var systolicBPLast = 140F
    var diastolicBPLast = 85F
    var exerciseTypeLast = "running"
    var exerciseFreqLast = 3
    var exerciseTimeLast = 30
    var exerciseNoteLast = ""
    var stepsGoalLast = 10000

    // Local state for form fields
    var entryDate by rememberSaveable { mutableStateOf(userHealthMetricsNewest.entryDate) }
    var weight by rememberSaveable { mutableStateOf(userHealthMetricsNewest.weight) }
    var height by rememberSaveable { mutableStateOf(userHealthMetricsNewest.height) }
    // val bmi by rememberSaveable { mutableStateOf(userHealthMetricsNewest.bmi) }
    var waist by rememberSaveable { mutableStateOf(userHealthMetricsNewest.waist) }
    var systolicBP by rememberSaveable { mutableStateOf(userHealthMetricsNewest.systolicBP) }
    var diastolicBP by rememberSaveable { mutableStateOf(userHealthMetricsNewest.diastolicBP) }
    Column(modifier = Modifier
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
//                    Text(text = "Goal: $stepsGoal steps/day")
//                    Spacer(modifier = Modifier.height(8.dp))
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
        Column(modifier = Modifier) {
            // Left Card - Weight, BMI, Waist
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    // Display comparison for Weight
                    MetricComparison("Weight", weightLast, userHealthMetricsNewest.weight, "kg")
                    // Display comparison for BMI
                    Text(text = bmi ?: "Calculating BMI...")
//                    MetricComparison("BMI", bmiLast, userHealthMetricsNewest.bmi, "kg/m2")
                    // Display comparison for Waist
                    MetricComparison("Waist", waistLast, userHealthMetricsNewest.waist, "cm")
                }
            // Right Card - Blood Pressure
                Column(modifier = Modifier.weight(1f)) {
                    // Display comparison for Blood Pressure (Systolic)
                    MetricComparison("Systolic BP", systolicBPLast, userHealthMetricsNewest.systolicBP, "mmHg")
                    // Display comparison for Blood Pressure (Diastolic)
                    MetricComparison("Diastolic BP", diastolicBPLast, userHealthMetricsNewest.diastolicBP, "mmHg")
                }
            }
        }
    }
}

@Composable
fun MetricComparison(metricName: String, lastValue: Float, newValue: Float, unit: String) {
    // Define normal ranges for each metric - these are illustrative and should be adjusted based on medical advice
    val normalRanges = mapOf(
        "Weight" to 50f..100f,
        "BMI" to 18.5f..24.9f,
        "Waist" to 70f..102f, // For men; would be different for women
        "Systolic BP" to 90f..120f,
        "Diastolic BP" to 60f..80f
    )
    val inRange = newValue in (normalRanges[metricName] ?: 0f..0f)
    val changeText = String.format("%.1f", abs(newValue - lastValue))
    val statusText = if (inRange) "Within Normal Range" else "Out of Normal Range"
    val statusColor = if (inRange) Color(android.graphics.Color.BLUE) else Color(android.graphics.Color.RED)
    val metricTextStyle = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        color = Color(android.graphics.Color.DKGRAY)
    )
    val statusTextStyle = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        color = statusColor
    )
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$metricName", style = metricTextStyle)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 0.dp)
            ) {
                Text("$lastValue", style = metricTextStyle)
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "direction")
                Text("$newValue $unit", style = metricTextStyle)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 0.dp)
            ) {
                if (newValue > lastValue) Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "up")
                else if (newValue == lastValue) Text(text = "-- ")
                else Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "down")
                Text(text = "$changeText $unit", style = metricTextStyle)
            }
            Text(text = "$statusText", style = statusTextStyle)
        }
    }
}
