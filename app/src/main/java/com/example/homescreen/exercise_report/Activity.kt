package com.example.homescreen.exercise_report

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)
