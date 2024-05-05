package com.example.homescreen.exercise_report

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "User_activity")
data class UserActivity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val activityId: Int,
    val distance: Float,
    val duration: Float,
    val route: String,
    val avgPace: Float,
    val elevation: Float,
    val startTime: Date,
    val endTime: Date
)
