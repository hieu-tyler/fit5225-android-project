package com.example.homescreen.nutrition

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NutritionTracker(navController: NavController) {
    var showForm by remember { mutableStateOf(false) }
    var showCreate by remember { mutableStateOf(false) }
    var showBackButton by remember { mutableStateOf(true) }
    var selectedFood by remember { mutableStateOf<Food?>(null) } // Hold the selected food item

    // TODO: Create retrieve function
    val dummyFoods = listOf(
        Food(1, "Apple", "apple", 95, 0.5f, 25f, 0.3f),
        Food(2, "Banana", "banana", 105, 1.3f, 27f, 0.4f),
        Food(3, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
        Food(4, "Salmon Fillet", "salmon", 220, 25.0f, 0.0f, 14.0f),
        Food(5, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
        Food(6, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
    )
    val foods by remember { mutableStateOf(dummyFoods) }

    Scaffold(
        topBar = {
            AnimatedVisibility(visible = !showForm) {
                TopAppBar(
                    title = { Text("Nutrition") },
                    actions = {
                        if (!showCreate) {
                            IconButton(onClick = { showCreate = true; showForm = false }) {
                                Icon(Icons.Default.Add, contentDescription = "Add Food")
                            }
                        } else {
                            IconButton(onClick = { showCreate = false; selectedFood = null; showForm = false }) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        }
                    },
                    navigationIcon = {
                        if (showBackButton) {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back")
                            }
                        }
                    },
                )
            }
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)
        ) {

            if (showForm && selectedFood != null) {
                NutritionFormView(navController, food = selectedFood!!)
                showBackButton = true
            }

            else if (showCreate && !showForm) {
                CreateNutritionForm(onCloseForm = {
                    showCreate = false; selectedFood = null; showForm = false })
            }

            else if (!showForm && selectedFood == null){
                FoodList(foods = foods) { clickedFood ->
                    selectedFood = clickedFood
                    showForm = true
                    navController.navigate("foodDetail/${clickedFood.id}")
                }
            }
        }
    }
}
