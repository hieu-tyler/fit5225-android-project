package com.example.homescreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.rememberImagePainter

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
fun NutritionTracker() {
    var showForm by remember { mutableStateOf(false) }
    val dummyFoods = listOf(
        Food(1, "Apple", "https://picsum.photos/id/1/200/300", 95, 0.5f, 25f, 0.3f),
        Food(2, "Banana", "https://picsum.photos/id/2/200/300", 105, 1.3f, 27f, 0.4f),
        Food(3, "Chicken Breast", "https://picsum.photos/id/3/200/300", 165, 31.0f, 0.0f, 3.6f),
        Food(4, "Salmon Fillet", "https://picsum.photos/id/4/200/300", 220, 25.0f, 0.0f, 14.0f)
    )
    val foods by remember { mutableStateOf(dummyFoods) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nutrition Tab",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(12.dp)) },
                actions = {
                    IconButton(onClick = { showForm = true }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Add Food")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 42.dp)
        ) {
            FoodList(foods = foods) {}
            if (showForm) {
                NutritionTrackerScreen()
            }
        }
    }
}

@Composable
fun FoodList(foods: List<Food>, onFoodClick: (Food) -> Unit) {
    LazyColumn {
        itemsIndexed(foods) { _, food ->
            FoodListItem(food = food, onFoodClick = onFoodClick)
        }
    }
}

@Composable
fun FoodListItem(food: Food, onFoodClick: (Food) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onFoodClick(food) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(food.imageUrl), // Placeholder image
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(85.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(text = food.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.padding(end = 6.dp)) {
                    Column (modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Calories: ${food.calories} Kj")
                    }
                    Column(modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Protein: ${food.protein}g")

                    }
                }
                Row(modifier = Modifier.padding(top = 6.dp)) {
                    Column (modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Carbs: ${food.carbs}g")
                    }
                    Column (modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Fats: ${food.fats}g")
                    }
                }
            }
        }
    }
}