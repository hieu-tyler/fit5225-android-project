package com.example.homescreen.exercise_report

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exercise(navController: NavHostController, viewModel: ViewModel) {
    val exercises = viewModel.allNames.observeAsState()
    var isExpanded by remember { mutableStateOf(false) }
    var isStarted by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf("exercise") }
    val subNavController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {isExpanded = it}) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .focusProperties {
                            canFocus = false
                        }
                        .padding(bottom = 8.dp),
                    readOnly = true,
                    value = selectedExercise.toString(),
                    onValueChange = {},
                    label = { Text(text = "Select Exercise") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false}) {
                    exercises.value?.forEach() { selectionOption ->
                        DropdownMenuItem (
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedExercise = selectionOption
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ,
                onClick = { navController.navigate(Routes.Map.value) }
            ) {
                if (!isStarted) {
                    Text(text = "Start Exercise")
                }else {
                    Text(text = "Stop Exercise")
                }
            }

            Spacer(modifier = Modifier.size(30.dp))
            Text("Enjoy your $selectedExercise", style =
            MaterialTheme.typography.bodyLarge)
            ExerciseReport(viewModel = viewModel)
        }
    }
}


fun updateActivityData(viewModel: ViewModel) {
    var activities = viewModel.allActivities.value?.get(1)
    activities = activities!!.copy(distance = activities.distance + 20, duration = activities.duration + 1, elevation = 2)
    println(activities)
    viewModel.updateActivity(activities)
}