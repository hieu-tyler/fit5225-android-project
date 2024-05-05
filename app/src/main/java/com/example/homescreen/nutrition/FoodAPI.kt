package com.example.homescreen.nutrition

data class FoodAPI(
    val name: String,
    val calories: Int,
    val serving_size_g: Int,
    val fat_total_g: Int,
    val fat_saturated_g: Int,
    val protein_g: Int,
    val sodium_mg: Int,
    val potassium_mg: Int,
    val cholesterol_mg: Int,
    val carbohydrates_total_g: Int,
    val fiber_g: Int,
    val sugar_g: Int
)
