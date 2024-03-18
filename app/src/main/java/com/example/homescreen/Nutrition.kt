package com.example.homescreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun NutritionTrackerScreen() {
    var foodName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fats by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Log Food Intake",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input fields for food log
        OutlinedTextField(
            value = foodName,
            onValueChange = { foodName = it },
            label = { Text("Food Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text("Calories") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Focus on the next field
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = protein,
            onValueChange = { protein = it },
            label = { Text("Protein (g)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Focus on the next field
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = carbs,
            onValueChange = { carbs = it },
            label = { Text("Carbs (g)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Focus on the next field
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fats,
            onValueChange = { fats = it },
            label = { Text("Fats (g)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Hide the keyboard
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        Button(
            onClick = {
                // Save food log to database or perform desired action
                Toast.makeText(context, "Food Logged: $foodName", Toast.LENGTH_SHORT).show()
            },
        ) {
            Text(text = "Save")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Monitor Nutritional Goals",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Placeholder for nutritional goals monitoring
        Text(
            text = "Your Nutritional Goals will appear here.",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Share Recipes or Dietary Tips",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Placeholder for sharing recipes or tips
        Text(
            text = "Share your favorite recipes or dietary tips with the community.",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun NutritionTrackerApp() {
    var showForm by remember { mutableStateOf(false) }
    val dummyFoods = listOf(
        Food(1, "Apple", "https://example.com/apple.jpg", 95, 0.5f, 25f, 0.3f),
        Food(2, "Banana", "https://example.com/banana.jpg", 105, 1.3f, 27f, 0.4f),
        Food(3, "Chicken Breast", "https://example.com/chicken.jpg", 165, 31.0f, 0.0f, 3.6f),
        Food(4, "Salmon Fillet", "https://example.com/salmon.jpg", 220, 25.0f, 0.0f, 14.0f)
    )
    var foods by remember { mutableStateOf(dummyFoods) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nutrition Tracker") },
                actions = {
                    IconButton(onClick = { showForm = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Food")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            FoodList(foods = foods) { food ->
                // Handle item click here
            }

            if (showForm) {
                NutritionTrackerScreen()
            }
        }
    }
}

//@Preview()
//@Composable
//fun NutritionTrackerApp() {
//    Surface(color = MaterialTheme.colorScheme.background) {
////        NutritionTrackerScreen()
//    }
//}