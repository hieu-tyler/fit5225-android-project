package com.example.homescreen.exercise_report

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.homescreen.ViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTrackerScreen(navHostController: NavHostController, viewModel: ViewModel) {

    val activities by viewModel.allUserActivities.observeAsState(emptyList())
    val distances by viewModel.allDistances.observeAsState()
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Activity Screen",
                    style = MaterialTheme.typography.headlineMedium) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

    ) {
        Column(modifier = Modifier.fillMaxSize())
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.padding(24.dp))
                Text(
                    text = "Total Activities this week",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                ActivityItemsList(activities = activities)

            }

            if (distances != null && distances!!.isNotEmpty()) {
                Text(
                    text = "Weekly activities distance graph",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                BarChartScreen(distances)
            }
            else {
                Text(
                    text = "No activities to show",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi :: class)
@Composable
fun ActivityItemsList(activities: List<UserActivity>) {
    
    LazyRow {
        itemsIndexed(activities) {index, activity ->
            ActivityItems(index, activity = activity)

            HorizontalDivider(thickness = 5.dp, color = Color.Blue)
        }
    }
}


@Composable
fun ActivityItems(index: Int, activity: UserActivity) {

    var lastItemPadding = 8.dp

    if (index - 1 == 0)
    {
        lastItemPadding = 16.dp
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp, end = lastItemPadding),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .wrapContentSize(Alignment.Center)
                .clickable { }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,

        ){
            Row {

                Text(
                    text = activity.startTime.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp, end = 24.dp)
                )

                Icon(Icons.Outlined.Info, contentDescription = null)
            }
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                Column (modifier = Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = " Distance: ${activity.distance}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = " Duration: ${activity.duration}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = " Average Pace: ${activity.avgPace}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = " Elevation: ${activity.elevation}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BarChartScreen(distances: List<Float>?) {
    val today = LocalDate.now()
    val size = distances?.size
    val barEntries = listOf(
        BarEntry(0f, size?.coerceAtLeast(1)?.let { distances[it.minus(1)] } ?: 0f),
        BarEntry(1f, size?.coerceAtLeast(2)?.let { distances[it.minus(2)] } ?: 0f),
        BarEntry(2f, size?.coerceAtLeast(3)?.let { distances[it.minus(3)] } ?: 0f),
        BarEntry(3f, size?.coerceAtLeast(4)?.let { distances[it.minus(4)] } ?: 0f),
        BarEntry(4f, size?.coerceAtLeast(5)?.let { distances[it.minus(5)] } ?: 0f),
        BarEntry(5f, size?.coerceAtLeast(6)?.let { distances[it.minus(6)] } ?: 0f),
        BarEntry(6f,size?.coerceAtLeast(7)?.let { distances[it.minus(7)] } ?: 0f)
    )
    val barDataSet = BarDataSet(barEntries, "Steps")
    barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
    val barData = BarData(barDataSet)
    barData.barWidth = 0.5f
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter =
                    IndexAxisValueFormatter(listOf("Today",
                        "Yesterday",
                        "${today.minusDays(2).dayOfMonth} / ${today.minusDays(2).month}",
                        "${today.minusDays(3).dayOfMonth} / ${today.minusDays(3).month}",
                        "${today.minusDays(4).dayOfMonth} / ${today.minusDays(4).month}",
                        "${today.minusDays(5).dayOfMonth} / ${today.minusDays(5).month}",
                        "${today.minusDays(6).dayOfMonth} / ${today.minusDays(6).month}",
                        ))
                animateY(4000)
            }
        }
    )
}
