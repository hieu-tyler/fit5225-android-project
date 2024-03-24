package com.example.homescreen

data class Activity(
    val id: Long,
    val name: String,
    val distance: Long,
    val duration: Long,
    val route: String,
    val avg_pace: Double,
    val elevation: Int,
)
