package com.example.homescreen.nutrition

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "personal_nutrition",
    foreignKeys = [ForeignKey(entity = Food::class,
        parentColumns = ["name"],
        childColumns = ["foodName"],
        onDelete = ForeignKey.CASCADE)])
data class PersonalNutrition(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val date: String,
    val category: String,
    val foodName: String,
    var quantity: Int,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fats: Float
)

