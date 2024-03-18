package com.example.homescreen

data class Food(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fats: Float
)
