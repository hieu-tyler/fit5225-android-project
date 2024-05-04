package com.example.homescreen.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "userProfile")
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
    val allowHealthDataShare: Boolean
){
    companion object {
        // Provides a default, empty instance of UserProfile
        fun empty(): UserProfile = UserProfile(
            userId = "",
            firstName = "",
            lastName = "",
            email = "",
            password = "",
            selectedGender = "",
            phone = "",
            birthDate = Date(),
            allowLocation = false,
            allowActivityShare = false,
            allowHealthDataShare = false
        )
    }
}