package com.example.homescreen.health_metrics

import java.util.Date

data class UserHealthMetrics(
    val entryDate: Date,
    val weight: Float,
    val height: Float,
    val bmi: Float,
    val waist: Float,
    val systolicBP: Float,
    val diastolicBP: Float,
)