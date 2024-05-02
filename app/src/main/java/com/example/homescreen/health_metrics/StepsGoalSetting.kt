package com.example.homescreen.health_metrics

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.pow
import com.example.homescreen.ui.theme.HomeScreenTheme

@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsGoalSettingsScreen(
    userHealthMetrics: UserHealthMetrics,
    onSaveMetrics: (UserHealthMetrics) -> Unit,
    navController: NavController
) {
    // Local state for form fields
    var userId by rememberSaveable { mutableStateOf(userHealthMetrics.userId) }
    val calendar = Calendar.getInstance()
    var entryDate by rememberSaveable { mutableStateOf(calendar.timeInMillis) }
    var weight by rememberSaveable { mutableStateOf(userHealthMetrics.weight) }
    var height by rememberSaveable { mutableStateOf(userHealthMetrics.height) }
    val bmi = weight / (height / 100).pow(2) // BMI calculation
    var waist by rememberSaveable { mutableStateOf(userHealthMetrics.waist) }
    var systolicBP by rememberSaveable { mutableStateOf(userHealthMetrics.systolicBP) }
    var diastolicBP by rememberSaveable { mutableStateOf(userHealthMetrics.diastolicBP) }
    val exerciseTypeList = listOf("running", "walking", "cycling")
    var exerciseType by rememberSaveable { mutableStateOf(exerciseTypeList[0]) }
    var exerciseFreq by rememberSaveable { mutableStateOf(userHealthMetrics.exerciseFreq) }
    var exerciseTime by rememberSaveable { mutableStateOf(userHealthMetrics.exerciseTime) }
    var exerciseNote by rememberSaveable { mutableStateOf(userHealthMetrics.exerciseNote) }
    var stepsGoal by rememberSaveable { mutableStateOf(userHealthMetrics.stepsGoal) }

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
                onValueChangeFinished = {
                    // When the user stops interacting with the slider, this will be called
                    onSaveMetrics(userHealthMetrics.copy(stepsGoal = stepsGoal))
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { onSaveMetrics(UserHealthMetrics(userId, Date(entryDate), weight, height, waist, bmi, systolicBP, diastolicBP, exerciseType, exerciseFreq, exerciseTime, exerciseNote, stepsGoal)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Records")
            }
        }
    }
}
