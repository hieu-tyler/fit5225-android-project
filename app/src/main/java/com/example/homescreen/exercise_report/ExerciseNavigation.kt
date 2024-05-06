package com.example.homescreen.exercise_report

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseNavigation(navHostController: NavHostController, viewModel: ViewModel) {
    val subNavController = rememberNavController()
    Column {
        NavHost(navController = subNavController, startDestination = Routes.ExerciseScreen.value) {
            composable(Routes.ExerciseScreen.value) {
                ExerciseScreen(navController = subNavController, viewModel = viewModel)
            }
            composable(Routes.MapScreen.value) {
                MapScreen(subNavController, viewModel)
            }
        }
    }
}