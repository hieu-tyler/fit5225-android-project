@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalNutrition(navController: NavController) {
    // TODO: Create retrieve function
    var carbsPercentage by remember { mutableStateOf(0.5f) }
    var carbsLimit by remember { mutableStateOf(206) }
    var fatPercentage by remember { mutableStateOf(0.5f) }
    var fatLimit by remember { mutableStateOf(206) }
    var proteinPercentage by remember { mutableStateOf(0.5f) }
    var proteinLimit by remember { mutableStateOf(206) }

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
                .padding(6.dp)
        ) {

            Card{
                Column(modifier = Modifier
                    .fillMaxWidth()
                ) {
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
                        ProgressBarWithPercentage(carbsPercentage)
                        Text("${carbsPercentage * carbsLimit}/${carbsLimit} g")
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text("Fat")
                        ProgressBarWithPercentage(fatPercentage)
                        Text("${fatPercentage * fatLimit}/${fatLimit} g")
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text("Protein")
                        ProgressBarWithPercentage(proteinPercentage)
                        Text("${proteinPercentage * proteinLimit}/${proteinLimit} g")
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

@Preview(showBackground = true)
@Composable
fun PersonalNutritionPreview() {
    var navController = rememberNavController()
    PersonalNutrition(navController)
}