package com.example.homescreen.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val selectedGender: String,
    val phone: String,
    val birthDate: Date,
    val allowLocation: Boolean,
    val allowActivityShare: Boolean,
    val allowHealthDataShare: Boolean,
    val isGoogleUser: Boolean = false,
    var profileImageUrl: String
)