package com.example.homescreen.exercise_report


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel
import com.google.android.gms.maps.GoogleMap
import java.sql.Time
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(navController: NavController, viewModel: ViewModel) {
    val exercises = viewModel.allNames.observeAsState()
    var isExpanded by remember { mutableStateOf(false) }
    var isStarted by remember { mutableStateOf(false) }
    var showActivity by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf("exercise") }

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
                    onValueChange = {  },
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
                onClick = { isStarted = true }
            ) {
                Text(text = "Start Exercise")
            }
            if (isStarted) {
                StartExercise(navController, selectedExercise)
                isStarted = false
            }
            Spacer(modifier = Modifier.size(30.dp))
            if (!showActivity) {
                Button(
                    modifier = Modifier
                        .padding(16.dp),

                    onClick = { showActivity = true }
                ) {
                    Text(text = "Show activities")
                }
            }
            else {

                if (!activityList(viewModel = viewModel)) {
                    showActivity = false
                }
            }

        }
    }
}


@Composable
fun StartExercise(navController: NavController, selectedExercise: String) {
    if (selectedExercise == "exercise") {
        Toast.makeText(
            LocalContext.current,
            "Exercise not selected", Toast.LENGTH_SHORT).show()
    }
    else {
        navController.navigate(Routes.MapScreen.value)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateUpdate(viewModel: ViewModel, navController: NavController, startTimestamp: Long) {
    val endTimeStamp = System.currentTimeMillis()
    val activityId = viewModel.getActivityId("Walking")
    viewModel.insertUserActivity(
        UserActivity(
            userId = 0,
            activityId = 1,
            duration = 30F,
            distance = 500F,
            avgPace = 500F/30F,
            elevation = 3F,
            startTime = Time.from(Instant.ofEpochMilli(startTimestamp)),
            endTime = Time.from(Instant.ofEpochMilli(endTimeStamp)),
            route = ""
        )
    )
    navController.navigate(Routes.ExerciseScreen.value)

}
