package com.example.homescreen.health_metrics

import android.util.Log
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.example.homescreen.ViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.pow

@Composable
fun UserHealthDashboard(
    userId: String, stepsTaken: Int, actualExerciseFreq: Int,
    actualExerciseTime: Int, userHealthMetrics: List<UserHealthMetrics>?,
    navController: NavController, viewModel: ViewModel
) {
    // Default values for initialization
    val defaultMetrics = UserHealthMetrics(0L, "", Date(), 0f, 0f, 0f, 0f, 0f, 0f, "running", 0, 0, "", 5000)

    // Using the first item from the list if available, otherwise default
    val metrics = userHealthMetrics?.firstOrNull() ?: defaultMetrics

    var userId by rememberSaveable { mutableStateOf(metrics.userId) }
    var entryDate by rememberSaveable { mutableStateOf(metrics.entryDate) }
    var weight by remember { mutableStateOf(metrics.weight) }
    var height by remember { mutableStateOf(metrics.height) }
    val bmi = weight / (height / 100).pow(2) // BMI calculation
    var waist by remember { mutableStateOf(metrics.waist) }
    var systolicBP by remember { mutableStateOf(metrics.systolicBP) }
    var diastolicBP by remember { mutableStateOf(metrics.diastolicBP) }
    val exerciseTypeList = listOf("running", "walking", "cycling")
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var exerciseType by remember { mutableStateOf(metrics.exerciseType) }
    var exerciseFreq by remember { mutableStateOf(metrics.exerciseFreq) }
    var exerciseTime by remember { mutableStateOf(metrics.exerciseTime) }
    var exerciseNote by remember { mutableStateOf(metrics.exerciseNote) }
    var stepsGoal by remember { mutableStateOf(metrics.stepsGoal) }

    // Use the second item from the list if available, otherwise default
    val lastMetrics = userHealthMetrics?.getOrNull(1) ?: defaultMetrics

    var lastEntryDate by rememberSaveable { mutableStateOf(lastMetrics.entryDate) }
    var lastWeight by remember { mutableStateOf(lastMetrics.weight) }
    var lastHeight by remember { mutableStateOf(lastMetrics.height) }
    val lastBmi = lastWeight / (lastHeight / 100).pow(2) // BMI calculation
    var lastWaist by remember { mutableStateOf(lastMetrics.waist) }
    var lastSystolicBP by remember { mutableStateOf(lastMetrics.systolicBP) }
    var lastDiastolicBP by remember { mutableStateOf(lastMetrics.diastolicBP) }
    var lastExerciseType by remember { mutableStateOf(lastMetrics.exerciseType) }
    var lastExerciseFreq by remember { mutableStateOf(lastMetrics.exerciseFreq) }
    var lastExerciseTime by remember { mutableStateOf(lastMetrics.exerciseTime) }
    var lastExerciseNote by remember { mutableStateOf(lastMetrics.exerciseNote) }
    var lastStepsGoal by remember { mutableStateOf(lastMetrics.stepsGoal) }

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
        // Top Card 1 - Exercise goal
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val exerciseProgress = (actualExerciseFreq * actualExerciseTime).toFloat() / (exerciseFreq * exerciseTime)
                val exerciseProgressPercent = String.format("%.1f", exerciseProgress * 100)
                Text(text = "You've completed $exerciseProgressPercent% exercise!",
                    style = MaterialTheme.typography.titleLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    CircularProgressIndicator(
                        progress = exerciseProgress,
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 8.dp,
                        modifier = Modifier.padding(16.dp).size(80.dp)
                    )
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Goal: $exerciseType for $exerciseTime min, \n$exerciseFreq times/week")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(onClick = { navController.navigate("ExerciseGoalSettingsScreen") }) {
                            Text(
                                text = "Change your exercise goal",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
        // Top Card 2 - Steps
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val stepProgress = stepsTaken.toFloat() / 10000
                Text(text = "You've walked $stepsTaken steps today!",
                    style = MaterialTheme.typography.titleLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    CircularProgressIndicator(
                        progress = stepProgress,
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 8.dp,
                        modifier = Modifier.padding(16.dp).size(80.dp)
                    )
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Goal: $stepsGoal steps/day")
                    Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(onClick = { navController.navigate("StepsGoalSettingsScreen") }) {
                            Text(
                                text = "Change your steps goal",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
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
                    MetricComparison("Weight", lastWeight, weight, "kg")
                    // Display comparison for BMI
                    MetricComparison("BMI", lastBmi, bmi, "kg/m2")
                    // Display comparison for Waist
                    MetricComparison("Waist", lastWaist, waist, "cm")
                }
            // Right Card - Blood Pressure
                Column(modifier = Modifier.weight(1f)) {
                    // Display comparison for Blood Pressure (Systolic)
                    MetricComparison("Systolic BP", lastSystolicBP, systolicBP, "mmHg")
                    // Display comparison for Blood Pressure (Diastolic)
                    MetricComparison("Diastolic BP", lastDiastolicBP, diastolicBP, "mmHg")
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
