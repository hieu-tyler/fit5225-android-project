package com.example.homescreen.health_metrics

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import java.util.Date
import kotlin.math.pow

@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsGoalSettingsScreen(
    userHealthMetrics: List<UserHealthMetrics>?,
    onSaveMetrics: (UserHealthMetrics) -> Unit,
    navController: NavController, viewModel: ViewModel
) {
    // Default initial values
    val defaultMetrics = UserHealthMetrics(0L, "", Date(), 0f, 0f, 0f, 0f, 0f, 0f, "running", 0, 0, "", 5000)

    // Check if the list is not null and not empty, else use default
    val metrics = userHealthMetrics?.firstOrNull() ?: defaultMetrics

    // Local state for form fields
    var recordId by rememberSaveable { mutableStateOf(metrics.recordId) }
    var userId by rememberSaveable { mutableStateOf(metrics.userId) }
    var entryDate by rememberSaveable { mutableStateOf(metrics.entryDate) }
    var weight by rememberSaveable { mutableStateOf(metrics.weight) }
    var height by rememberSaveable { mutableStateOf(metrics.height) }
    val bmi = weight / (height / 100).pow(2) // BMI calculation
    var waist by rememberSaveable { mutableStateOf(metrics.waist) }
    var systolicBP by rememberSaveable { mutableStateOf(metrics.systolicBP) }
    var diastolicBP by rememberSaveable { mutableStateOf(metrics.diastolicBP) }
    val exerciseTypeList = listOf("running", "walking", "cycling")
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var exerciseType by rememberSaveable { mutableStateOf(metrics.exerciseType) }
    var exerciseFreq by rememberSaveable { mutableStateOf(metrics.exerciseFreq) }
    var exerciseTime by rememberSaveable { mutableStateOf(metrics.exerciseTime) }
    var exerciseNote by rememberSaveable { mutableStateOf(metrics.exerciseNote) }
    var stepsGoal by rememberSaveable { mutableStateOf(metrics.stepsGoal) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Change Steps Goal") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text("${stepsGoal} Steps")
            Slider(
                value = stepsGoal.toFloat(),
                onValueChange = { newValue ->
                    stepsGoal = newValue.toInt()
                },
                valueRange = 0f..100000f,
                steps = 19, // 100,000 / 5,000 - 1 = 19 steps
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { saveGoal(recordId, userId, entryDate, weight, height, waist, bmi, systolicBP, diastolicBP, exerciseType, exerciseFreq, exerciseTime, exerciseNote, stepsGoal, navController, viewModel) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Records")
            }
        }
    }
}
