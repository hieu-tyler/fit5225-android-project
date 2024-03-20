// HomeScreen.kt

package com.example.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String)

object PhysicalActivities : Screen("Physical Activities")
object NutritionalIntake : Screen("Nutritional Intake")
object HealthMetrics : Screen("Health Metrics")
object ProfileAndSettings : Screen("Profile and Settings")

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        NavHost(navController = navController, startDestination = PhysicalActivities.route) {
            composable(PhysicalActivities.route) { PhysicalActivitiesScreen() }
            composable(NutritionalIntake.route) { NutritionalIntakeScreen() }
            composable(HealthMetrics.route) { HealthMetricsScreen() }
            composable(ProfileAndSettings.route) { ProfileAndSettingsScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(PhysicalActivities, NutritionalIntake, HealthMetrics, ProfileAndSettings)
    BottomNavigation(
        backgroundColor = Color.Yellow, // Set the background color to yellow
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            val icon = getIconForScreen(screen)
            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = null) },
                label = {
                    Text(
                        text = screen.route,
                        textAlign = TextAlign.Center,
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route -> popUpTo(route) {
                            saveState = true
                        }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun getIconForScreen(screen: Screen): ImageVector {
    return when (screen) {
        PhysicalActivities -> Icons.Filled.Create
        NutritionalIntake -> Icons.Filled.Info
        HealthMetrics -> Icons.Filled.Star
        ProfileAndSettings -> Icons.Filled.Settings
        else -> error("Unhandled screen type: $screen")
    }
}

@Composable
fun PhysicalActivitiesScreen() {
    Text("Daily Activities")
}

@Composable
fun NutritionalIntakeScreen() {
    Text("Nutritional Intake")
}

@Composable
fun HealthMetricsScreen() {
    Text("Health Metrics")
}

@Composable
fun ProfileAndSettingsScreen() {
    Text("Profile and Settings")
}
