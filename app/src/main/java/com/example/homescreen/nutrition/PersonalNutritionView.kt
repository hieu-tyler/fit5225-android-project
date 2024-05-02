package com.example.homescreen.nutrition

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.compose.ui.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.homescreen.ViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalNutritionView(navController: NavController, viewModel: ViewModel) {
    var carbs by remember { mutableIntStateOf(0) }
    val carbsLimit by remember { mutableIntStateOf(325) }
    var fat by remember { mutableIntStateOf(0) }
    val fatLimit by remember { mutableIntStateOf(78) }
    var protein by remember { mutableIntStateOf(0) }
    val proteinLimit by remember { mutableIntStateOf(206) }
    var breakfastCalories by remember { mutableFloatStateOf(0f) }
    var lunchCalories by remember { mutableFloatStateOf(0f) }
    var dinnerCalories by remember { mutableFloatStateOf(0f) }
    var totalCalories by remember { mutableFloatStateOf(0f) }
    var dataReady by remember { mutableStateOf(false) }
    val allPersonalNutrition by viewModel.allPersonalNutrition.observeAsState(emptyList())

    LaunchedEffect(allPersonalNutrition) {
        var totalCarbs = 0.0f
        var totalProtein = 0.0f
        var totalFats = 0.0f
        for (nutrition in allPersonalNutrition) {
            when (nutrition.category) {
                "breakfast" -> breakfastCalories += calculateCalories(
                    nutrition.protein,
                    nutrition.fats,
                    nutrition.carbs)

                "lunch" -> lunchCalories += calculateCalories(
                    nutrition.protein,
                    nutrition.fats,
                    nutrition.carbs
                )

                "dinner" -> dinnerCalories += calculateCalories(
                    nutrition.protein,
                    nutrition.fats,
                    nutrition.carbs
                )
            }
            totalCarbs += nutrition.carbs
            totalProtein += nutrition.protein
            totalFats += nutrition.fats
        }
        carbs = totalCarbs.roundToInt()
        protein = totalProtein.roundToInt()
        fat = totalFats.roundToInt()
        totalCalories = breakfastCalories + lunchCalories + dinnerCalories
        Log.d(ContentValues.TAG, "carbs: $carbs, protein $protein, fat $fat, totalCalories $totalCalories")
        dataReady = totalCalories > 0
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nutrition Intake") },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            if (dataReady) {
                CaloriesStatCard(
                    carbs.toFloat(), carbsLimit,
                    fat.toFloat(), fatLimit,
                    protein.toFloat(), proteinLimit,
                    totalCalories)
                Spacer(modifier = Modifier.height(12.dp))

                BreakfastCard(navController, breakfastCalories)
                Spacer(modifier = Modifier.height(8.dp))
                LunchCard(navController, lunchCalories)
                Spacer(modifier = Modifier.height(8.dp))
                DinnerCard(navController, dinnerCalories)
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                // Show loading indicator or placeholder
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }

        }
    }
}

@Composable
fun CaloriesStatCard(carbs:Float, carbsLimit: Int, fat:Float, fatLimit: Int, protein:Float, proteinLimit: Int, totalCalories: Float) {
    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ){
        Column(modifier = Modifier
            .height(200.dp)
            .padding(4.dp)
            .fillMaxWidth()
        ) {
            PieChartCalories(
                carbs, carbsLimit,
                fat, fatLimit,
                protein, proteinLimit,
                totalCalories
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Text("Carbs")
                ProgressBarWithPercentage(carbs/carbsLimit)
                Text("${carbs}/${carbsLimit} g")
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Text("Fat")
                ProgressBarWithPercentage(fat/fatLimit)
                Text("${fat}/${fatLimit} g")
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Text("Protein")
                ProgressBarWithPercentage(protein/proteinLimit)
                Text("${protein}/${proteinLimit} g")
            }
        }
    }
}

@Composable
fun BreakfastCard(navController: NavController, breakfastCalories: Float) {
    val category = "breakfast"
    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Breakfast")
                Spacer(modifier = Modifier.height(4.dp))
                Text("$breakfastCalories KJ", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = {
                navController.navigate("foodList/$category")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun LunchCard(navController: NavController, lunchCalories: Float) {
    val category = "lunch"
    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Lunch")
                Spacer(modifier = Modifier.height(4.dp))
                Text("$lunchCalories KJ", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = {
                navController.navigate("foodList/$category")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun DinnerCard(navController: NavController, dinnerCalories: Float) {
    val category = "dinner"
    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Dinner")
                Spacer(modifier = Modifier.height(4.dp))
                Text("$dinnerCalories KJ", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = {
                navController.navigate("foodList/$category")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun ProgressBarWithPercentage(
    progress: Float
) {
    val indicatorColor = if (progress < 1f) {
            Color.Green
        } else {
            Color.Red
        }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .weight(1f)
                .height(8.dp),
            progress = progress,
            color = indicatorColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        PercentageText(progress = progress)
    }
}

@Composable
fun PercentageText(progress: Float) {
    val percentage = (progress * 100).toInt()
    Text(
        text = "$percentage%",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun PieChartCalories(carbs:Float, carbsLimit: Int, fat:Float, fatLimit: Int, protein:Float, proteinLimit: Int, totalCalories: Float) {
    val maxLimit = maxOf(carbsLimit, fatLimit, proteinLimit)
    val totalPercentage = (carbs / maxLimit) + (fat / maxLimit) + (protein / maxLimit)
    val carbPercentage = (carbs / maxLimit) / totalPercentage
    val fatPercentage = (fat / maxLimit) / totalPercentage
    val proteinPercentage = (protein / maxLimit) / totalPercentage
    val remainPercentage = 1f - (carbPercentage + fatPercentage + proteinPercentage)

    val pieEntries = listOf(
        PieEntry(carbPercentage * 100, "Carbs"),
        PieEntry(fatPercentage * 100, "Fat"),
        PieEntry(proteinPercentage * 100, "Protein"),
        PieEntry(remainPercentage * 100, "")
    )
    val pieDataSet = PieDataSet(pieEntries, "")
    val colors = mutableListOf<Int>()
    colors.add(Color.Red.toArgb())
    colors.add(Color.Green.toArgb())
    colors.add(Color.Blue.toArgb())
    colors.add(Color.White.toArgb())
    pieDataSet.colors = colors
    val pieData = PieData(pieDataSet)
    pieDataSet.xValuePosition =
        PieDataSet.ValuePosition.INSIDE_SLICE;
    pieDataSet.yValuePosition =
        PieDataSet.ValuePosition.INSIDE_SLICE;
    pieDataSet.valueFormatter = PercentValueFormatter()
    pieDataSet.valueTextSize = 25f
    pieDataSet.valueTextColor = Color.Black.toArgb()

    AndroidView(
        modifier = Modifier
            .height(256.dp)
            .fillMaxWidth(),
        factory = { context ->
            PieChart(context).apply {
                data = pieData
                description.isEnabled = false
                centerText = "Calories\n$totalCalories KJ"
                setDrawCenterText(true)
                setEntryLabelTextSize(16f)
                animateY(2000)
                legend.isEnabled = false
            }
        }
    )
}

class PercentValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "${value.toInt()}%"
    }
}

fun calculateCalories(proteinGrams: Float, fatGrams: Float, carbGrams: Float): Int {
    val proteinCalories = proteinGrams * 4
    val fatCalories = fatGrams * 9
    val carbCalories = carbGrams * 4
    val totalCalories = proteinCalories + fatCalories + carbCalories
    return totalCalories.roundToInt()
}

