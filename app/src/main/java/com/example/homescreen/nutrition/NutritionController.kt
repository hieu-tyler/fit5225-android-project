package com.example.homescreen.nutrition

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homescreen.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NutritionListView(navController: NavController, viewModel: ViewModel, category: String) {
    var showForm by remember { mutableStateOf(false) }
    var showCreate by remember { mutableStateOf(false) }
    var showBackButton by remember { mutableStateOf(true) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }
    val foods by viewModel.allFoods.observeAsState(emptyList())
    val allPersonalNutrition by viewModel.allPersonalNutrition.observeAsState(emptyList())
    val quantityMap = remember { mutableStateMapOf<Food, Int>() }

    // Fetch foods from the ViewModel when the composable is first launched
    LaunchedEffect(Unit) {
        // Handle Food
        Log.d(ContentValues.TAG, "Food crawling")

        if (viewModel.allFoods.value?.isEmpty() == true) {
            if (foods.isEmpty()) {
                try {
                    val defaultFoods = prepareFoodList(viewModel)
                    viewModel.insertFoods(defaultFoods)
                } catch (e: SQLiteConstraintException) {
                    Log.d(ContentValues.TAG, "Error SQL Unique Constraints")
                }
            }
        }
        if (quantityMap.isEmpty()) {
            quantityMap.apply {
                foods.forEach { food -> put(food, 0) }
            }
        }
    }
    LaunchedEffect(allPersonalNutrition) {
        for (nutrition in allPersonalNutrition) {
            val currentFood = foods.find {it.name == nutrition.foodName}
            if (currentFood != null && category == nutrition.category) {
                quantityMap[currentFood] = quantityMap[currentFood]!! + 1
                Log.d(ContentValues.TAG, "Quantity $currentFood updated - ${quantityMap[currentFood]}")
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
                        IconButton(onClick = {
                            saveNutrition(viewModel, quantityMap, category)
                            navController.popBackStack()
                        }) {
                            Text("Save")
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
        },
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
                FoodList(
                    foodEntities = foods,
                    quantityMap = quantityMap,
                ) { clickedFood ->
                    selectedFood = clickedFood
                    showForm = true
                    navController.navigate("foodDetail/${clickedFood.name}")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun saveNutrition(viewModel: ViewModel, quantityMap: SnapshotStateMap<Food, Int>, category: String) {
    val currentDate = LocalDate.now().toString()
    val personalNutritionList = mutableListOf<PersonalNutrition>()

    for ((food, quantity) in quantityMap) {
        val existingPersonalNutrition = viewModel.allPersonalNutrition.value?.find {
            it.foodName == food.name && it.category == category
        } // TODO: Add condition for more user and date

        when {
            quantity == 0 && existingPersonalNutrition != null -> {
                viewModel.deletePersonalNutrition(existingPersonalNutrition)
            }
            quantity != 0 && existingPersonalNutrition != null -> {
                existingPersonalNutrition.apply {
                    this.quantity = quantity
                    this.calories = food.calories * quantity
                    this.protein = food.protein * quantity
                    this.carbs = food.carbs * quantity
                    this.fats = food.fats * quantity
                }
                viewModel.updatePersonalNutrition(existingPersonalNutrition)
            }
            quantity != 0 && existingPersonalNutrition == null -> {
                val personalNutrition = PersonalNutrition(
                    userName = "user", // TODO: Replace with actual username
                    date = currentDate,
                    category = category,
                    foodName = food.name,
                    quantity = quantity,
                    calories = food.calories * quantity,
                    protein = food.protein * quantity,
                    carbs = food.carbs * quantity,
                    fats = food.fats * quantity
                )
                personalNutritionList.add(personalNutrition)
            }
        }
    }

    personalNutritionList.forEach {
        viewModel.insertPersonalNutrition(it)
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

// TODO: Clean getFoodFactApi function
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

suspend fun prepareFoodList(viewModel: ViewModel): List<Food> {
    val foodName = getDefaultFoodName()
    viewModel.getResponse(foodName)
    val response = viewModel.retrofitResponse

    val foodEntities = mutableListOf<Food>()
    for (foodAPI in response.value.items) {
        val name = foodAPI.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        val imageUrl = name.lowercase().replace(" ", "_")
        val calories = foodAPI.calories
        val protein = foodAPI.protein_g.toFloat()
        val carbs = foodAPI.carbohydrates_total_g.toFloat()
        val fats = foodAPI.fat_total_g.toFloat()

        Log.d(ContentValues.TAG, name)

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
    return foodEntities
}




