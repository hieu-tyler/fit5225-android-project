package com.example.homescreen.nutrition

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
@SuppressLint("DiscouragedApi", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NutritionFormView(navController: NavController, food: Food) {
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
                .padding(64.dp)
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
//                    val newFood = Food(
//                        id = System.currentTimeMillis(),
//                        name = foodName,
//                        imageUrl = imageUrl,
//                        calories = calories.toIntOrNull() ?: 0,
//                        protein = protein.toFloatOrNull() ?: 0f,
//                        carbs = carbs.toFloatOrNull() ?: 0f,
//                        fats = fats.toFloatOrNull() ?: 0f
//                    )
//                    foodName = ""
//                    calories = ""
//                    protein = ""
//                    carbs = ""
//                    fats = ""
//                    imageUrl = ""
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
fun FoodList(foodEntities: List<Food>, quantityMap: MutableMap<Food, Int>, onFoodClick: (Food) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(foodEntities) { _, food ->
            FoodListItem(
                food = food,
                quantity = quantityMap[food] ?: 0,
                onIncrease = { quantityMap[food] = (quantityMap[food] ?: 0) + 1 },
                onDecrease = { if ((quantityMap[food] ?: 0) > 0) quantityMap[food] = (quantityMap[food] ?: 0) - 1 },
                onFoodClick = onFoodClick
            )
        }
    }
}

@SuppressLint("DiscouragedApi", "MutableCollectionMutableState")
@Composable
fun FoodListItem(
    food: Food,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onFoodClick: (Food) -> Unit
) {
    val context = LocalContext.current
    val resourceId: Int = context.resources.getIdentifier(food.imageUrl, "drawable", context.packageName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onFoodClick(food) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(resourceId),
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(72.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = food.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Carbs: ${food.carbs} g", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Fats: ${food.fats} g", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Protein: ${food.protein} g", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Calories: ${food.calories} kcal", style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrease quantity
                IconButton(onClick = { onDecrease() }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Remove")
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Display quantity
                Text(text = "$quantity", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.width(8.dp))

                // Increase quantity
                IconButton(onClick = { onIncrease() }) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Add")
                }
            }
        }
    }
}