package com.example.homescreen.exercise_report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color

@Composable
fun ExerciseReport(activityViewModel: ActivityViewModel) {
    val activities by activityViewModel.allActivities.observeAsState(emptyList())
    val selectedActivity = remember { mutableStateOf<Activity?>(null) }
    val insertDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { insertDialog.value = true }) {
            Text("Add Activity")
        }
        LazyColumn {
            itemsIndexed(activities) { index, activity ->
                if (index % 5 == 0) {
                    ActivityItemTitle()
                }
                ActivityItem(
                    activity = activity,
                    onEdit = { selectedActivity.value = activity },
                    onDelete = { activityViewModel.deleteActivity(activity) }
                )
                Divider(color = Color.Blue, thickness = 5.dp)
            }
        }
    }

    if (insertDialog.value) {
        InsertActivityDialog(
            onDismiss = { insertDialog.value = false },
            onSave = { activityName ->
                activityViewModel.insertActivity(Activity(name = activityName, distance = 0, duration = 0, avg_pace = 0.0, elevation = 0, route = ""))
            }
        )
    }
    selectedActivity.value?.let { activity ->
        EditActivityDialog(
            activity = activity,
            onDismiss = { selectedActivity.value = null },
            onSave = { updatedActivity ->
                activityViewModel.updateActivity(updatedActivity)
                selectedActivity.value = null
            }
        )
    }
}


@Composable
fun InsertActivityDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var activityName by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Activity") },
        confirmButton = {
            Button(
                onClick = {
                    onSave(activityName)
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            TextField(
                value = activityName,
                onValueChange = { activityName = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}
@Composable
fun EditActivityDialog(activity: Activity, onDismiss: () -> Unit, onSave: (Activity) -> Unit)
{
    var editedActivity by remember { mutableStateOf(activity) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Activity") },
        confirmButton = {
            Button(
                onClick = {
                    onSave(editedActivity)
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            TextField(
                value = editedActivity.name.toString(),
                onValueChange = { editedActivity = editedActivity.copy(name = it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}