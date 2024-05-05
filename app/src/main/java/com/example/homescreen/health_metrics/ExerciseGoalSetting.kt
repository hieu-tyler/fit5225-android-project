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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
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
fun ExerciseGoalSettingsScreen(
    userHealthMetrics: List<UserHealthMetrics>?,
    onSaveMetrics: (UserHealthMetrics) -> Unit,
    navController: NavController, viewModel: ViewModel
) {
    // Default values for initialization
    val defaultMetrics = UserHealthMetrics(0L, "", Date(), 0f, 0f, 0f, 0f, 0f, 0f, "running", 0, 0, "", 5000)

    // Using the first item from the list if available, otherwise default
    val metrics = userHealthMetrics?.firstOrNull() ?: defaultMetrics

    // State variables initialized from metrics
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
                title = { Text(text = "Change Exercise Goal") },
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
            ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .focusProperties {
                            canFocus = false
                        }
                        .padding(bottom = 8.dp),
                    readOnly = true,
                    value = exerciseType,
                    onValueChange = {},
                    label = { Text("Exercise Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false })
                {
                    exerciseTypeList.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                exerciseType = selectionOption
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            IntMetricSlider(
                label = "Exercise Frequency: ${exerciseFreq} time(s)",
                value = exerciseFreq,
                onValueChange = { exerciseFreq = it },
                valueRange = 0..7
            )
            IntMetricSlider(
                label = "Exercise Time: ${exerciseTime} minute(s)",
                value = exerciseTime,
                onValueChange = { exerciseTime = it },
                valueRange = 0..180
            )
            OutlinedTextField(
                value = exerciseNote,
                onValueChange = { exerciseNote = it },
                label = { Text("Exercise Goal Note") },
                modifier = Modifier.fillMaxWidth()
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

fun saveGoal(recordId: Long, userId: String, entryDate: Date, weight: Float, height: Float,
             waist: Float, bmi: Float, systolicBP: Float, diastolicBP: Float, exerciseType: String,
             exerciseFreq: Int, exerciseTime: Int, exerciseNote: String, stepsGoal: Int,
             navController: NavController, viewModel: ViewModel
) {
    if (userId.isNotEmpty()) {
        val userHealthMetrics = UserHealthMetrics(
            recordId = recordId,
            userId = userId,
            entryDate = entryDate,
            weight = weight,
            height = height,
            waist = waist,
            bmi = bmi,
            systolicBP = systolicBP,
            diastolicBP = diastolicBP,
            exerciseType = exerciseType,
            exerciseFreq = exerciseFreq,
            exerciseTime = exerciseTime,
            exerciseNote = exerciseNote,
            stepsGoal = stepsGoal
        )
        viewModel.updateUserHealthMetrics(userHealthMetrics)
        navController.navigate(Routes.HealthMetrics.value)  // Navigate to login screen after successful registration
    } else {
        Log.e("Create HealthMetrics", "Failed to create HealthMetrics record.")
    }
}