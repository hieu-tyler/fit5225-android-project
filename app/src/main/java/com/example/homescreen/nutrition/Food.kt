package com.example.homescreen.nutrition

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val imageUrl: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fats: Float
)
