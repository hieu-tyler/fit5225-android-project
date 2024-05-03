package com.example.homescreen.health_metrics

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserHealthMetricsDAO {

    @Query("SELECT * FROM user_health_metrics WHERE userId = :userId")
    fun getUserHealthMetrics(userId: Int): Flow<List<UserHealthMetrics>>

    @Insert
    suspend fun insertUserHealthMetrics(metrics: UserHealthMetrics)

    @Update
    suspend fun updateUserHealthMetrics(metrics: UserHealthMetrics)

    @Delete
    suspend fun deleteUserHealthMetrics(metrics: UserHealthMetrics)
}