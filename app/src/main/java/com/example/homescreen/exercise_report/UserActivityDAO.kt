package com.example.homescreen.exercise_report

import android.service.autofill.AutofillService
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserActivityDAO {
    @Query("SELECT * FROM User_activity")
    fun getAllUserActivities(): Flow<List<UserActivity>>

    @Query("SELECT * FROM User_activity WHERE userId = :userId")
    fun getUserActivities(userId : Int): Flow<List<UserActivity>>

    @Query("SELECT distance FROM USER_ACTIVITY")
    fun getAllDistance(): Flow<List<Float>>

    @Insert
    suspend fun insertUserActivity(userActivity: UserActivity)
    @Update
    suspend fun updateUserActivity(userActivity: UserActivity)
    @Delete
    suspend fun deleteUserActivity(userActivity: UserActivity)

    @Query("DELETE FROM USER_ACTIVITY")
    fun deleteAllUserActivity()
}