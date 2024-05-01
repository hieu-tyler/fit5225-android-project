package com.example.homescreen.nutrition

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.homescreen.ViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalNutrition(navController: NavController, viewModel: ViewModel) {
    // TODO: Create retrieve function
    var carbs by remember { mutableIntStateOf(25) }
    var carbsLimit by remember { mutableIntStateOf(206) }
    var fat by remember { mutableIntStateOf(34) }
    var fatLimit by remember { mutableIntStateOf(206) }
    var protein by remember { mutableIntStateOf(55) }
    var proteinLimit by remember { mutableIntStateOf(206) }
    var breakfastCalories by remember { mutableFloatStateOf(500.0f) }
    var lunchCalories by remember { mutableFloatStateOf(300.0f) }
    var dinnerCalories by remember { mutableFloatStateOf(200.0f) }
    val allPersonalNutrition by viewModel.allPersonalNutrition.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nutrition Record") },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            CaloriesStatCard(
                carbs.toFloat(), carbsLimit,
                fat.toFloat(), fatLimit,
                protein.toFloat(), proteinLimit)
            Spacer(modifier = Modifier.height(12.dp))

            BreakfastCard(navController, breakfastCalories)
            Spacer(modifier = Modifier.height(8.dp))
            LunchCard(navController, lunchCalories)
            Spacer(modifier = Modifier.height(8.dp))
            DinnerCard(navController, dinnerCalories)
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}

@Composable
fun CaloriesStatCard(carbs:Float, carbsLimit: Int, fat:Float, fatLimit: Int, protein:Float, proteinLimit: Int) {
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
                protein, proteinLimit
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
                ProgressBarWithPercentage(carbs.toFloat()/carbsLimit)
                Text("${carbs}/${carbsLimit} g")
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Text("Fat")
                ProgressBarWithPercentage(fat.toFloat()/fatLimit)
                Text("${fat}/${fatLimit} g")
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Text("Protein")
                ProgressBarWithPercentage(protein.toFloat()/proteinLimit)
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
                navController.navigate("foodList")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun DinnerCard(navController: NavController, dinnerCalories: Float) {
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
                navController.navigate("foodList")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun ProgressBarWithPercentage(
    progress: Float,
) {
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
fun PieChartCalories(carbs:Float, carbsLimit: Int, fat:Float, fatLimit: Int, protein:Float, proteinLimit: Int) {
    val calories = calculateCalories(protein, fat, carbs)
    val carbsPrintValue =  carbs / carbsLimit * 100
    val fatPrintValue =  fat / fatLimit * 100
    val proteinPrintValue =  protein / proteinLimit * 100
    val remain = 100f - (carbsPrintValue + fatPrintValue + proteinPrintValue)

    val pieEntries = listOf(
        PieEntry(carbsPrintValue, "carbs"),
        PieEntry(fatPrintValue, "fat"),
        PieEntry(proteinPrintValue, "protein"),
        PieEntry(remain, ""),
    )
    val pieDataSet = PieDataSet(pieEntries, "")
    val colors = mutableListOf<Int>()
    colors.add(Color.parseColor("#FF5722"))
    colors.add(Color.parseColor("#4CAF50"))
    colors.add(Color.parseColor("#2196F3"))
    colors.add(Color.WHITE)
    pieDataSet.colors = colors
    val pieData = PieData(pieDataSet)
    pieDataSet.xValuePosition =
        PieDataSet.ValuePosition.INSIDE_SLICE;
    pieDataSet.yValuePosition =
        PieDataSet.ValuePosition.INSIDE_SLICE;
    pieDataSet.valueFormatter = PercentValueFormatter()
    pieDataSet.valueTextSize = 20f

    AndroidView(
        modifier = Modifier
            .height(256.dp)
            .fillMaxWidth(),
        factory = { context ->
            PieChart(context).apply {
                data = pieData
                description.isEnabled = false
                centerText = "Calories\n$calories KJ"
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

fun calculateCalories(proteinGrams: Float, fatGrams: Float, carbGrams: Float): Float {
    val proteinCalories = proteinGrams * 4
    val fatCalories = fatGrams * 9
    val carbCalories = carbGrams * 4
    return proteinCalories + fatCalories + carbCalories
}

