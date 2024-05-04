package com.example.homescreen.exercise_report

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import com.example.homescreen.ViewModel
import java.sql.Time
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun activityList(viewModel: ViewModel): Boolean {
    val activities by viewModel.allActivities.observeAsState(emptyList())
    val selectedActivity = remember { mutableStateOf<Activity?>(null) }
    val insertDialog = remember { mutableStateOf(false) }
    val closeDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row (modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = { insertDialog.value = true }) {
                Text("Add Activity")
            }
            Button(onClick = { closeDialog.value = true }) {
                Text("Hide Activities")
            }
        }

        LazyColumn {
            itemsIndexed(activities) { index, activity ->
                ActivityItem(
                    activity = activity,
                    onEdit = { selectedActivity.value = activity },
                    onDelete = { viewModel.deleteActivity(activity) }
                )
                HorizontalDivider(thickness = 5.dp, color = Color.Blue)
            }
        }
    }

    if (closeDialog.value) {
        return false
    }


    if (insertDialog.value) {
        InsertActivityDialog(
            onDismiss = { insertDialog.value = false },
            onSave = { activityName ->
                viewModel.insertActivity(
                    Activity(name = activityName)
                )
            }
        )
    }

    selectedActivity.value?.let { activity ->
        EditActivityDialog(
            activity = activity,
            onDismiss = { selectedActivity.value = null },
            onSave = { updatedActivity ->
                viewModel.updateActivity(updatedActivity)
                selectedActivity.value = null
            }
        )
    }
    return true
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
fun EditActivityDialog(
    activity: Activity,
    onDismiss: () -> Unit,
    onSave: (Activity) -> Unit
) {
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

@RequiresApi(Build.VERSION_CODES.O)
fun addUserActivity(viewModel: ViewModel) {
    viewModel.insertUserActivity(
        UserActivity(
            distance = 0F,
            duration = 0F,
            elevation = 0F,
            route = "",
            avgPace = 0F,
            activityId = 0,
            userId = 0,
            startTime = Time.from(Instant.now()),
            endTime = Time.from(Instant.now())
        )
    )
}