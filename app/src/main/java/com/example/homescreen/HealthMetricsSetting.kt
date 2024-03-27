package com.example.homescreen

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
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import com.example.homescreen.ui.theme.HomeScreenTheme
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.pow

@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthMetricsSettingsScreen(
    userHealthMetrics: UserHealthMetrics,
    onSaveMetrics: (UserHealthMetrics) -> Unit, // Callback when Save button is clicked
    onSignOut: () -> Unit // Callback when Sign Out button is clicked
) {
    // Local state for form fields
    var userId by rememberSaveable { mutableStateOf(userHealthMetrics.userId) }
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var entryDate by rememberSaveable { mutableStateOf(calendar.timeInMillis) }
    var weight by rememberSaveable { mutableStateOf(userHealthMetrics.weight) }
    var height by rememberSaveable { mutableStateOf(userHealthMetrics.height) }
    val bmi = weight / (height / 100).pow(2) // BMI calculation
    var waist by rememberSaveable { mutableStateOf(userHealthMetrics.waist) }
    val exerciseTypeList = listOf("running", "walking", "cycling")
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var exerciseType by rememberSaveable { mutableStateOf(exerciseTypeList[0]) }
    var exerciseFreq by rememberSaveable { mutableStateOf(userHealthMetrics.exerciseFreq) }
    var exerciseTime by rememberSaveable { mutableStateOf(userHealthMetrics.exerciseTime) }
    var exerciseNote by rememberSaveable { mutableStateOf(userHealthMetrics.exerciseNote) }
    var systolicBP by rememberSaveable { mutableStateOf(userHealthMetrics.systolicBP) }
    var diastolicBP by rememberSaveable { mutableStateOf(userHealthMetrics.diastolicBP) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Create New Health Record",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 12.dp).padding(bottom = 12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) { Text(text = "Enter Record Date") }
            Spacer(modifier = Modifier.width(12.dp))
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            Text(
                text = "${formatter.format(Date(entryDate))}",
                modifier = Modifier.weight(1f)
            )
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        entryDate = datePickerState.selectedDateMillis!!
                    }) { Text(text = "OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text(text = "Cancel") }
                }
            ) { DatePicker(state = datePickerState) }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Health Metrics",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        FloatMetricSlider(
            value = weight,
            onValueChange = { weight = it },
            label = "Weight: ${weight.toInt()} kg",
            valueRange = 30f..200f
        )
        FloatMetricSlider(
            label = "Height: ${height.toInt()} cm",
            value = height,
            onValueChange = { height = it },
            valueRange = 120f..220f
        )
        // BMI Display (calculated from weight and height, not adjustable by slider)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("BMI: ${"%.1f".format(bmi)} kg/m2",
                style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(24.dp))
        FloatMetricSlider(
            label = "Waist Circumference: ${waist.toInt()} cm",
            value = waist,
            onValueChange = { waist = it },
            valueRange = 40f..150f
        )
        FloatMetricSlider(
            label = "Systolic Blood Pressure: ${systolicBP.toInt()} cm",
            value = systolicBP,
            onValueChange = { systolicBP = it },
            valueRange = 50f..250f
        )
        FloatMetricSlider(
            label = "Diastolic Blood Pressure: ${diastolicBP.toInt()} cm",
            value = diastolicBP,
            onValueChange = { diastolicBP = it },
            valueRange = 30f..200f
        )
        Text(
            text = "Exercise Goal",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
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
            onClick = { onSaveMetrics(UserHealthMetrics(userId, Date(entryDate), weight, height, waist, bmi, exerciseType, exerciseFreq, exerciseTime, exerciseNote, systolicBP, diastolicBP)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Records")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}

@Composable
fun FloatMetricSlider(label: String, value: Float, onValueChange: (Float) -> Unit, valueRange: ClosedFloatingPointRange<Float>) {
    Column {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth(),
            steps = (valueRange.endInclusive - valueRange.start).toInt() - 1
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun IntMetricSlider(label: String, value: Int, onValueChange: (Int) -> Unit, valueRange: IntRange) {
    Column {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        // Convert Int value to Float for the Slider
        Slider(
            value = value.toFloat(),
            onValueChange = { newValue ->
                // Convert Float back to Int and pass to onValueChange
                onValueChange(newValue.toInt())
            },
            valueRange = valueRange.start.toFloat()..valueRange.endInclusive.toFloat(),
            steps = (valueRange.endInclusive - valueRange.start) - 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@RequiresApi(64)
@Preview(showBackground = true)
@Composable
fun CreateHealthMetricsRecord() {
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
        onSaveMetrics = {}, // In preview, actions don't need to do anything
        onSignOut = {} // In preview, actions don't need to do anything
    )
}