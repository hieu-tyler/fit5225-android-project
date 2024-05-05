package com.example.homescreen.health_metrics

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_health_metrics")
data class UserHealthMetrics(
    @PrimaryKey(autoGenerate = true)
    val recordId: Long = 0,
    val userId: String,
    val entryDate: Date,
    val weight: Float,
    val height: Float,
    val bmi: Float,
    val waist: Float,
    val systolicBP: Float,
    val diastolicBP: Float,
    val exerciseType: String,
    val exerciseFreq: Int,
    val exerciseTime: Int,
    val exerciseNote: String,
    val stepsGoal: Int
)