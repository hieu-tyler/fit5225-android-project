package com.example.homescreen.exercise_report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActivityItem(activity: Activity, onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = activity.name, modifier = Modifier.weight(1f))
        Text(text = activity.distance.toString(), modifier = Modifier.weight(1f))
        Text(text = activity.duration.toString(), modifier = Modifier.weight(1f))
        Text(text = activity.avg_pace.toString(), modifier = Modifier.weight(1f))
        Text(text = activity.elevation.toString(), modifier = Modifier.weight(1f))

    }
}