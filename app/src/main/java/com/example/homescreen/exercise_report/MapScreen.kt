package com.example.homescreen.exercise_report

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel
import java.sql.Time
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapScreen(navController: NavController, viewModel: ViewModel) {
    val startTimestamp =System.currentTimeMillis()
    Box (
        modifier = Modifier.fillMaxSize()
    ) {

        MapView()

        FilledTonalButton(
            onClick = { calculateUpdate(viewModel,navController, startTimestamp) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.error)
        ) {
            Text(text = "Stop Exercise")
        }
    }
}

