package com.example.homescreen.profile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDAO {
    @Query("SELECT * FROM userProfile")
    fun getAllUsers(): Flow<List<UserProfile>>

    @Insert
    suspend fun insertUser(userProfile: UserProfile)

    @Update
    suspend fun updateUser(userProfile: UserProfile)

    @Delete
    suspend fun deleteUser(userProfile: UserProfile)
}