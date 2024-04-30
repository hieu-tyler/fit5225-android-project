package com.example.homescreen.nutrition

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NutritionTracker(navController: NavController, foodViewModel: FoodViewModel) {
    var showForm by remember { mutableStateOf(false) }
    var showCreate by remember { mutableStateOf(false) }
    var showBackButton by remember { mutableStateOf(true) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }
    val foods by foodViewModel.allFoods.observeAsState(emptyList())

    // Fetch foods from the ViewModel when the composable is first launched
    LaunchedEffect(Unit) {
        if (foodViewModel.allFoods.value?.isEmpty() == true) {
            if (foods.isEmpty()) {
                try {
                    val defaultFoods = prepareFoodList()
                    foodViewModel.insertFoods(defaultFoods)
                } catch (e: SQLiteConstraintException) {
                    Log.d(ContentValues.TAG, "Error SQL Unique Constraints")
                }
            }
        }
    }

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
//                        IconButton(onClick = { foodViewModel.deleteAll() }) {
//                            Icon(Icons.Default.Delete, contentDescription = "Delete")
//                        }
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
                    navController.navigate("foodDetail/${clickedFood.name}")
                }
            }
        }
    }
}

fun getDefaultFoodName(): String {
    val fruits = listOf(
        "apple",
        "banana",
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
        "Cucumber",
        "Onions",
        "Potatoes",
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
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val imageUrl = jsonObject.getString("name").lowercase().replace(" ", "_")
            val calories = jsonObject.getString("calories").toFloatOrNull()?.toInt() ?: 0
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

