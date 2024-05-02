package com.example.homescreen.health_metrics

import java.util.Date

data class UserHealthMetrics(
    val userId: Int,
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