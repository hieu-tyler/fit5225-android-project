package com.example.homescreen.exercise_report

data class Activity(
    val id: Long,
    val name: String,
    val distance: Long,
    val duration: Long,
    val route: String,
    val avg_pace: Double,
    val elevation: Int,
)
