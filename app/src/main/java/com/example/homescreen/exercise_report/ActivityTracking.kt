package com.example.homescreen.exercise_report

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import com.example.homescreen.R
import com.example.homescreen.ViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTrackerScreen(viewModel: ViewModel) {

    val activities by viewModel.allActivities.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activity Screen") },
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
            Text(
                text = "Weekly activities distance graph",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            BarChartScreen()
        }
    }

}

@OptIn(ExperimentalComposeUiApi :: class)
@Composable
fun ActivityItemsList(activities: List<Activity>) {
    
    LazyRow {
        itemsIndexed(activities) {index, activity ->
            ActivityItems(index, activity = activity)

            Divider(color = Color.Blue, thickness = 5.dp)
        }
    }
}


@Composable
fun ActivityItems(index: Int, activity: Activity) {

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
                .width(200.dp)
                .height(150.dp)
                .clickable { }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,

        ){
            Row {

                Text(
                    text = activity.name,
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
                        text = " Average Pace: ${activity.avg_pace}",
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

@Composable
fun MapScreen() {

    Text(
        text = "Map",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(16.dp)
    )

    Image(
        painter = rememberImagePainter(R.drawable.walkingroute), // Placeholder image
        contentDescription = "Map",
        modifier = Modifier
            .size(400.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )

}

@Composable
fun BarChartScreen() {
    val barEntries = listOf(
        BarEntry(0f, 1070f),
        BarEntry(1f, 4050f),
        BarEntry(2f, 3890f),
        BarEntry(3f, 5599f),
        BarEntry(4f, 2300f),
        BarEntry(5f, 4055f),
        BarEntry(6f,5100f)
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
                    IndexAxisValueFormatter(listOf("Sun", "Mon", "Tues", "Wed", "Thurs",
                        "Fri","Sat"))
                animateY(4000)
            }
        }
    )
}