package com.example.homescreen.login

import java.util.Date

data class UserProfile(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val selectedGender: String,
    val phone: String,
    val birthDate: Date,
    val allowLocation: Boolean = false,
    val allowActivityShare: Boolean = false,
    val allowHealthDataShare: Boolean = false,
    val isGoogleUser: Boolean = false
)