package com.example.homescreen.profile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDAO {
    @Query("SELECT * FROM user_profile WHERE userId = :userId")
    fun getUserById(userId: String): UserProfile

    @Query("SELECT * FROM user_profile WHERE email = :email LIMIT 1")
    fun getUserProfileByEmail(email: String): UserProfile?

    @Query("SELECT * FROM user_profile")
    fun getAllUsers(): Flow<List<UserProfile>>

    @Insert
    suspend fun insertUser(userProfile: UserProfile)

    @Update
    suspend fun updateUser(userProfile: UserProfile)

    @Query("UPDATE user_profile SET isGoogleUser = :isGoogle WHERE userId = :userId")
    fun updateIsGoogleUser(userId: String, isGoogle: Boolean)

    @Query("UPDATE user_profile SET profileImageUrl = :imageUrl WHERE userId = :userId")
    fun updateProfileImage(userId: String, imageUrl: String)

    @Delete
    suspend fun deleteUser(userProfile: UserProfile)
}