package com.example.homescreen

import java.util.Date

data class UserHealthMetrics(
    val userId: Int,
    val entryDate: Date,
    val weight: Float,
    val height: Float,
    val bmi: Float,
    val waist: Float,
    val exerciseType: String,
    val exerciseFreq: Int,
    val exerciseTime: Int,
    val exerciseNote: String,
    val systolicBP: Float,
    val diastolicBP: Float
)