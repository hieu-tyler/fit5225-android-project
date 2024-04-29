package com.example.homescreen.nutrition

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NutritionTracker(navController: NavController) {
    var showForm by remember { mutableStateOf(false) }
    var showCreate by remember { mutableStateOf(false) }
    var showBackButton by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf<Food?>(null) } // Hold the selected food item

    // TODO: Create retrieve function
    val dummyFoods = listOf(
        Food(1, "Apple", "apple", 95, 0.5f, 25f, 0.3f),
        Food(2, "Banana", "banana", 105, 1.3f, 27f, 0.4f),
        Food(3, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
        Food(4, "Salmon Fillet", "salmon", 220, 25.0f, 0.0f, 14.0f),
        Food(6, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
        Food(8, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
    )
    val foods by remember { mutableStateOf(dummyFoods) }

    Scaffold(
        topBar = {
            AnimatedVisibility(visible = !showForm) {
                TopAppBar(
                    title = { Text("Nutrition Tab") },
                    actions = {
                        IconButton(onClick = { showCreate = true; showForm = false }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Food")
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
                NutritionFormView(navController, food = selectedFood!!, onCloseForm = { selectedFood = null; showForm = false })
                showBackButton = true
            }

            if (showCreate && !showForm) {
                CreateNutritionForm(onCloseForm = { showCreate = false })
            }

            if (!showForm && selectedFood == null){
                FoodList(foods = foods) { clickedFood ->
                    selectedFood = clickedFood
                    showForm = true
                    navController.navigate("foodDetail/${clickedFood.id}")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DiscouragedApi", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NutritionFormView(navController: NavController, food: Food, onCloseForm: () -> Unit) {
    val description by remember { mutableStateOf("The banana (Musa genus) is a remarkable fruit, cherished across the globe for its flavor, nutritional value, and year-round availability.") }
    val context = LocalContext.current
    val resourceId: Int = context.resources.getIdentifier(food.imageUrl, "drawable", context.packageName)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = food.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back")
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
            Image(
                painter = painterResource(resourceId),
                contentDescription = "Food Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(512.dp, 255.dp)
                    .background(Color.Gray)
            )

            // Description text
            if (description != "") {
                Text(
                    text = description,
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Nutritional information card
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    NutritionInfoItem(label = "Protein", value = "${food.protein} g")
                    NutritionInfoItem(label = "Calories", value = "${food.calories} kJ")
                    NutritionInfoItem(label = "Carbs", value = "${food.carbs} g")
                    NutritionInfoItem(label = "Fats", value = "${food.fats} g")
                }
            }
        }
    }
}

@Composable
fun NutritionInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value)
    }
}

@Composable
fun CreateNutritionForm(onCloseForm: () -> Unit) {
    var foodName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fats by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add New Food",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row {
            // Image preview
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(128.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Input fields for food attributes
            Column {
                OutlinedTextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
        Column {
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
                    val newFood = Food(
                        id = System.currentTimeMillis(),
                        name = foodName,
                        imageUrl = imageUrl,
                        calories = calories.toIntOrNull() ?: 0,
                        protein = protein.toFloatOrNull() ?: 0f,
                        carbs = carbs.toFloatOrNull() ?: 0f,
                        fats = fats.toFloatOrNull() ?: 0f
                    )
                    foodName = ""
                    calories = ""
                    protein = ""
                    carbs = ""
                    fats = ""
                    imageUrl = ""
                    Toast.makeText(context, "Food Logged: $foodName", Toast.LENGTH_SHORT).show()

                    onCloseForm()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Food")
            }
        }
    }
}


@Composable
fun FoodList(foods: List<Food>, onFoodClick: (Food) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(foods) { _, food ->
            FoodListItem(food = food, onFoodClick = onFoodClick)
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun FoodListItem(food: Food, onFoodClick: (Food) -> Unit) {
    val context = LocalContext.current
    val resourceId: Int = context.resources.getIdentifier(food.imageUrl, "drawable", context.packageName)
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
                painter = rememberImagePainter(resourceId),
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(text = food.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.padding(end = 6.dp)) {
                    Column (modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Calories: ${food.calories} Kj")
                    }
                    Column(modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Protein: ${food.protein} g")

                    }
                }
                Row(modifier = Modifier.padding(top = 6.dp)) {
                    Column (modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Carbs: ${food.carbs} g")
                    }
                    Column (modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = "Fats: ${food.fats} g")
                    }
                }
            }
        }
    }
}