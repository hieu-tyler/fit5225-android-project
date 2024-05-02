package com.example.homescreen.exercise_report

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel

@Composable
fun ExerciseNavigation(navHostController: NavHostController, viewModel: ViewModel) {
    val subNavController = rememberNavController()
    Column {
        NavHost(navController = subNavController, startDestination = Routes.Exercise.value) {
            composable(Routes.Exercise.value) {
                Exercise(navController = subNavController, viewModel = viewModel)
            }
            composable(Routes.Map.value) {
                MapScreen(subNavController, viewModel)
            }
        }
    }
}