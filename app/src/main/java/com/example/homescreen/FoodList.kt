package com.example.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun FoodList(foods: List<Food>, onFoodClick: (Food) -> Unit) {
    LazyColumn {
        itemsIndexed(foods) { _, food ->
            FoodListItem(food = food,onFoodClick = onFoodClick)
        }
    }
}

@Composable
fun FoodListItem(food: Food, onFoodClick: (Food) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onFoodClick(food) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(food.imageUrl), // Placeholder image
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = food.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Calories: ${food.calories}")
                Text(text = "Protein: ${food.protein}g")
                Text(text = "Carbs: ${food.carbs}g")
                Text(text = "Fats: ${food.fats}g")
            }
        }
    }
}