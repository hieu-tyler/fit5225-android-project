package com.example.homescreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.vector.ImageVector
data class NavBarItem (
    val label : String = "",
    val icon : ImageVector = Icons.Filled.AddCircle,
    val route : String = ""
) {
    fun NavBarItems(): List<NavBarItem> {
        return listOf(
//            NavBarItem(
//                label = "Home",
//                icon = Icons.Filled.Home,
//                route = Routes.Home.value
//            ),
            NavBarItem(
                label = "Health Metrics",
                icon = Icons.Filled.Home,
                route = Routes.HealthMetrics.value
            ),
            NavBarItem(
                label = "Exercise Report",
                icon = Icons.Filled.DateRange,
                route = Routes.ExerciseReport.value
            ),
            NavBarItem(
                label = "Start",
                icon = Icons.Filled.Add,
                route = Routes.ExerciseNav.value
            ),
            NavBarItem(
                label = "Nutrition",
                icon = Icons.Filled.CheckCircle,
                route = Routes.Nutrition.value
            ),
            NavBarItem(
                label = "Profile",
                icon = Icons.Filled.Person,
                route = Routes.Profile.value
            )
        )
    }
}