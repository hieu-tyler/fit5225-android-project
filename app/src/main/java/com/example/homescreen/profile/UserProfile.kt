package com.example.homescreen.profile

import java.util.Date

data class UserProfile(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val selectedGender: String,
    val phone: String,
    val birthDate: Date,
    val allowLocation: Boolean,
    val allowActivityShare: Boolean,
    val allowHealthDataShare: Boolean
)