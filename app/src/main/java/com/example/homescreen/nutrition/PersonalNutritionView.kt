package com.example.homescreen.nutrition

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalNutrition(navController: NavController) {
    // TODO: Create retrieve function
    var carbs by remember { mutableIntStateOf(25) }
    var carbsLimit by remember { mutableIntStateOf(206) }
    var fat by remember { mutableIntStateOf(34) }
    var fatLimit by remember { mutableIntStateOf(206) }
    var protein by remember { mutableIntStateOf(55) }
    var proteinLimit by remember { mutableIntStateOf(206) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nutrition Record") },
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
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

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
                        carbs.toFloat(), carbsLimit,
                        fat.toFloat(), fatLimit,
                        protein.toFloat(), proteinLimit
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
            progress = progress,
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
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

    val pieEntries = listOf(
        PieEntry(carbsPrintValue, "carbs"),
        PieEntry(fatPrintValue, "fat"),
        PieEntry(proteinPrintValue, "protein"),
    )
    val pieDataSet = PieDataSet(pieEntries, "")
    pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
    val pieData = PieData(pieDataSet)
    pieDataSet.xValuePosition =
        PieDataSet.ValuePosition.INSIDE_SLICE;
    pieDataSet.yValuePosition =
        PieDataSet.ValuePosition.INSIDE_SLICE;
    pieDataSet.valueFormatter = PercentValueFormatter()
    pieDataSet.valueTextSize = 40f

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
                setEntryLabelTextSize(12f)
                animateY(4000)
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

@Preview(showBackground = true)
@Composable
fun PersonalNutritionPreview() {
    var navController = rememberNavController()
    PersonalNutrition(navController)
}