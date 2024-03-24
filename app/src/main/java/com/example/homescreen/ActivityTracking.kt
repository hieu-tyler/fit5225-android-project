package com.example.homescreen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.homescreen.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTrackerScreen(navHostController: NavHostController) {

    val activitiesList = listOf(
        Activity(1, "Walking", 12, 185, "", 0.25, 0),
        Activity(2, "Running", 5, 30, "", 0.6, 0),
        Activity(3, "Cycling", 0, 0, "", 0.0, 0)
    )

    val activities by remember { mutableStateOf(activitiesList) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activity Screen") }
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
            Text(text = "Total Activities this week",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp))
            ActivityItemsList(activities = activities)
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

    var lastItemPadding = 0.dp

    if (index - 1 == 0)
    {
        lastItemPadding = 16.dp
    }

    Box(modifier = Modifier
        .padding(start = 16.dp, end = lastItemPadding)) {
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .width(250.dp)
                .height(150.dp)
                .clickable { }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,

        ){
            Text(text = activity.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
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