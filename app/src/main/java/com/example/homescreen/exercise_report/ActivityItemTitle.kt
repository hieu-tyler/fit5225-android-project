package com.example.homescreen.exercise_report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActivityItemTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Name", modifier = Modifier.weight(1f))
        Text(text = "Distance", modifier = Modifier.weight(1f))
        Text(text = "Duration", modifier = Modifier.weight(1f))
        Text(text = "Avg_pace", modifier = Modifier.weight(1f))
        Text(text = "Elevation", modifier = Modifier.weight(1f))

    }
}