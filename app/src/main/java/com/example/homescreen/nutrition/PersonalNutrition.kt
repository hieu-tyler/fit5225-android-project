package com.example.homescreen.nutrition

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "personal_nutrition",
    foreignKeys = [ForeignKey(entity = Food::class,
        parentColumns = ["name"],
        childColumns = ["foodName"],
        onDelete = ForeignKey.NO_ACTION)])
data class PersonalNutrition(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val date: String,
    val category: String,
    val foodName: String,
    var quantity: Int,
    var calories: Int,
    var protein: Float,
    var carbs: Float,
    var fats: Float
)

