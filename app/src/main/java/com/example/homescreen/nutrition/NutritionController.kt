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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NutritionTracker(navController: NavController, foodViewModel: FoodViewModel) {
    var showForm by remember { mutableStateOf(false) }
    var showCreate by remember { mutableStateOf(false) }
    var showBackButton by remember { mutableStateOf(true) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }
//    var foods by remember { mutableStateOf<List<FoodEntity>>(emptyList()) }
    val foods by foodViewModel.allFoods.observeAsState(emptyList())
    // Fetch foods from the ViewModel when the composable is first launched
        LaunchedEffect(Unit) {
            if (foods.isEmpty()) {
                val defaultFoods = prepareFoodList()
                foodViewModel.insertFoods(defaultFoods)
            }
        }

    // TODO: Create retrieve function
//    val dummyFoodEntities = listOf(
//        FoodEntity(1, "Apple", "apple", 95, 0.5f, 25f, 0.3f),
//        FoodEntity(2, "Banana", "banana", 105, 1.3f, 27f, 0.4f),
//        FoodEntity(3, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
//        FoodEntity(4, "Salmon Fillet", "salmon", 220, 25.0f, 0.0f, 14.0f),
//        FoodEntity(5, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
//        FoodEntity(6, "Chicken Breast", "chicken", 165, 31.0f, 0.0f, 3.6f),
//    )
//    val dummyFoods by remember { mutableStateOf(dummyFoodEntities) }

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
                FoodList(foodEntities = foods) { clickedFood ->
                    selectedFood = clickedFood
                    showForm = true
                    navController.navigate("foodDetail/${clickedFood.id}")
                }
            }
        }
    }
}

fun getDefaultFoodName(): String {
    val fruits = listOf(
        "apples",
        "bananas",
        "oranges",
        "grapes",
        "strawberries"
    )
    val fruitsString = fruits.joinToString(separator = " ")
    val protein = listOf(
        "Chicken breast",
        "Ground beef",
        "Salmon",
        "Tofu",
        "Turkey breast",
        "Pork chops"
    )
    val proteinString = protein.joinToString(separator = " ")
    val vegetable = listOf(
        "Spinach",
        "Broccoli",
        "Carrots",
        "Tomatoes",
        "Capsicum",
        "Cucumbers",
        "Onions",
        "Garlic",
        "Potatoes",
        "Sweet potatoes",
        "Lettuce",
    )
    val vegetableString = vegetable.joinToString(separator = " ")

    return listOf(fruitsString, proteinString, vegetableString).joinToString(separator = " ")
}

suspend fun getFoodFactApi(foodName: String): Response {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.api-ninjas.com/v1/nutrition?query=${foodName}")
            .get()
            .addHeader("X-Api-Key", "PUBQhZ5p5CufNYJrC80wsw==NufwNlb4B2Gid3eO")
            .build()

        client.newCall(request).execute()
    }
}


fun prepareFoodList(): List<Food> {
    val foodName = getDefaultFoodName()
    val response = runBlocking {
        getFoodFactApi(foodName)
    }
    val foodEntities = mutableListOf<Food>()
    val jsonArray = JSONArray(response.body?.string())
    try {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val name = jsonObject.getString("name")
            val imageUrl = "" // TODO: Add logic to extract image URL if available
            val calories = jsonObject.getString("calories").toInt()
            val protein = jsonObject.getString("protein_g").toFloatOrNull() ?: 0f
            val carbs = jsonObject.getString("carbohydrates_total_g").toFloatOrNull() ?: 0f
            val fats = jsonObject.getString("fat_total_g").toFloatOrNull() ?: 0f

            val food = Food(
                name = name,
                imageUrl = imageUrl,
                calories = calories,
                protein = protein,
                carbs = carbs,
                fats = fats
                )
                foodEntities.add(food)
            }
    } catch (e: Exception) {
        // Handle JSON parsing exception
        e.printStackTrace()
    }
    return foodEntities
}

