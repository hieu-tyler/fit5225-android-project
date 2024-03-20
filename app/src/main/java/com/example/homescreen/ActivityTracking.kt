package com.example.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ActivityTrackerScreen() {

    val activitiesList = listOf(
        Activity(1, "Walking", 12, 185, "", 0.25, 0),
        Activity(2, "Running", 5, 30, "", 0.6, 0),
        Activity(3, "Cycling", 0, 0, "", 0.0, 0)
    )

    val activities by remember { mutableStateOf(activitiesList) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activity") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Activity Tracking",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )
//            ActivityItemsList(activities = activitiesList)
            MapScreen()
            ActivityItems(activity = activities[0])
            ActivityItems(activity = activities[1])
            ActivityItems(activity = activities[2])

        }
    }

}

@OptIn(ExperimentalComposeUiApi :: class)
@Composable
fun ActivityItemsList(activities: List<Activity>) {
    
    LazyColumn {
        item { 
            Text(text = "Working")
        }
        itemsIndexed(activities) {_, activity ->
            ActivityItems(activity = activity)
        }
    }
}

@Composable
fun ActivityItems(activity: Activity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Row {

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = activity.name, style = MaterialTheme.typography.titleMedium)
                Row {
                    Column {
                        Text(text = " Distance: ${activity.distance}")
                        Text(text = " Duration: ${activity.duration}")
                        Text(text = " Average Pace: ${activity.avg_pace}")
                        Text(text = " Elevation: ${activity.elevation}")
                    }

                }

            }
        }

    }

}

@Composable
fun MapScreen() {
    val mapUrl = "res/drawable/walkingroute.png"
    val map1Url = "https://m.media-amazon.com/images/I/81LP7DLisbL.png"

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