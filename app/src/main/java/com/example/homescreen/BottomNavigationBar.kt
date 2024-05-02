package com.example.homescreen

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.homescreen.exercise_report.ActivityTrackerScreen
import com.example.homescreen.exercise_report.Exercise
import com.example.homescreen.health_metrics.HealthMetricsSettingsScreen
import com.example.homescreen.health_metrics.HealthScreen
import com.example.homescreen.health_metrics.UserHealthDashboard
import com.example.homescreen.health_metrics.UserHealthMetrics
import com.example.homescreen.nutrition.NutritionFormView
import com.example.homescreen.nutrition.NutritionTracker
import com.example.homescreen.nutrition.PersonalNutritionView
import com.example.homescreen.profile.ProfileSettingsScreen
import com.example.homescreen.profile.UserProfile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BottomNavigationBar(navController: NavController) {
    return BottomNavigation (backgroundColor= MaterialTheme.colorScheme.background ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        NavBarItem().NavBarItems().forEach { navItem ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        navItem.icon, contentDescription = null
                    )
                },
                label = { Text(navItem.label) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == navItem.route
                } == true,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@RequiresApi(64)
@Composable
fun HomeScreen(viewModel: ViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController,
//            startDestination = Routes.Home.value,
            startDestination = Routes.HealthMetrics.value,
            Modifier.padding(paddingValues)
        ) {
//            composable(Routes.Home.value) {
//                HomeScreen()
//            }
            composable(Routes.HealthMetrics.value) {
                HealthScreen()
            }
//            composable(Routes.HealthMetrics.value) {
//                val sampleMetrics = UserHealthMetrics(
//                    userId = 1,
//                    entryDate = Date(),
//                    weight = 70f,
//                    height = 175f,
//                    bmi = 22.9f,
//                    waist = 87f,
//                    exerciseType = "running",
//                    exerciseFreq = 3,
//                    exerciseTime = 30,
//                    exerciseNote = "",
//                    systolicBP = 160f,
//                    diastolicBP = 95f
//                )
//                UserHealthDashboard(stepsTaken = 5500, actualExerciseFreq = 2,
//                    actualExerciseTime = 30, userHealthMetricsNewest = sampleMetrics, navController)
//            }
//            composable("HealthMetricsSettingsScreen") {
//                val sampleUserHealthMetrics = UserHealthMetrics(
//                    userId = 1,
//                    entryDate = Date(),
//                    weight = 60F,
//                    height = 170F,
//                    bmi = 20F,
//                    waist = 100F,
//                    exerciseType = "running",
//                    exerciseFreq = 3,
//                    exerciseTime = 30,
//                    exerciseNote = "",
//                    systolicBP = 120F,
//                    diastolicBP = 80F
//                )
//                HealthMetricsSettingsScreen(
//                    userHealthMetrics = sampleUserHealthMetrics,
//                    onSaveMetrics = {},
//                    navController
//                )
//            }

            /* Nutrition navigation tab */
            composable(Routes.Nutrition.value) {
                PersonalNutritionView(navController, viewModel)
            }
            composable(
                route = "foodList/{category}") {
                NutritionTracker(navController, viewModel)
            }
            composable(
                route = "foodDetail/{foodName}",
                arguments = listOf(navArgument("foodName") { type = NavType.StringType })
            ) { backStackEntry ->
                val foodName = backStackEntry.arguments?.getString("foodName")
                val selectedFood = viewModel.allFoods.value?.find { it.name == foodName }
                if (selectedFood != null) {
                    NutritionFormView(navController = navController, food = selectedFood)
                }
            }
            composable(Routes.ExerciseReport.value) {
                ActivityTrackerScreen(viewModel = viewModel)
            }
            composable(Routes.Exercise.value) {
                Exercise(navController, viewModel)
            }
            composable(Routes.Profile.value) {
                val sampleUserProfile = UserProfile(
                    userId = 1,
                    firstName = "John",
                    lastName = "Doe",
                    email = "johndoe@example.com",
                    password = "password123",
                    selectedGender = "Male",
                    phone = "0412345678",
                    birthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("01/01/1990") ?: Date(),
                    allowLocation = true,
                    allowActivityShare = true,
                    allowHealthDataShare = false
                )
                ProfileSettingsScreen(navController, sampleUserProfile, {}, {})
            }
        }

    }

}
