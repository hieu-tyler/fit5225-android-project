package com.example.homescreen.exercise_report

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.homescreen.R
import com.example.homescreen.ui.theme.Purple80


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTrackerScreen(navHostController: NavHostController) {

    val barChartInputsPercent = (0..10).map { (1..100).random().toFloat() }

    val activitiesList = listOf(
        Activity(1, "Walking", 12, 185, "", 0.25, 0),
        Activity(2, "Running", 5, 30, "", 0.6, 0),
        Activity(3, "Cycling", 0, 0, "", 0.0, 0)
    )

    val activities by remember { mutableStateOf(activitiesList) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activity Screen") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(24.dp))
            Text(text = "Total Activities this week",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp))
            ActivityItemsList(activities = activities)
            ReportGraph(data = mapOf(
                Pair(0.5f, 10),
                Pair(0.6f, 12),
                Pair(0.4f, 8),
                Pair(0.2f, 4),
                Pair(0.9f, 18),
            ), maxValue = 1000)
            MapScreen()

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
fun ReportGraph(
    data: Map<Float, Int>,
    maxValue: Int
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Text(text = "Graph", style = MaterialTheme.typography.headlineSmall)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {

                //scale
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = maxValue.toString())
                    Spacer(modifier = Modifier.fillMaxHeight())
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = (maxValue/2).toString())
                    Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                }
            }

            //graph
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.Black)
            )
             data.forEach {
                 Box(modifier = Modifier
                     .padding(start = 20.dp)
                     .clip(RoundedCornerShape(10.dp))
                     .width(20.dp)
                     .fillMaxHeight(it.key)
                     .background(Purple80)
                     .clickable {
                         Toast
                             .makeText(context, it.key.toString(), Toast.LENGTH_SHORT)
                             .show()
                     }
                 )
             }

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(Color.Black)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 72.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            data.values.forEach {
                Text(
                    modifier = Modifier.width(20.dp),
                    text = it.toString(),
                    textAlign = TextAlign.Center
                )
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