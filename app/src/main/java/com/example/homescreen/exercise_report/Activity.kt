package com.example.homescreen.exercise_report

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val distance: Long,
    val duration: Long,
    val route: String,
    val avg_pace: Double,
    val elevation: Int,
)
