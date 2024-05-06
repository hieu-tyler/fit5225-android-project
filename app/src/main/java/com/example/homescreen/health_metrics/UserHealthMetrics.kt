package com.example.homescreen.health_metrics

import java.util.Date

data class UserHealthMetrics(
    val entryDate: Date,
    var weight: Float,
    var height: Float,
    var waist: Float,
    var systolicBP: Float,
    var diastolicBP: Float
) {
    val bmi: Float
        get() = if (height > 0) weight / ((height / 100) * (height / 100)) else 0f
}