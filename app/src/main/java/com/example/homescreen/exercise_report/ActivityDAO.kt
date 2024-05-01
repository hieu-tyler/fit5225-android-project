package com.example.homescreen.exercise_report

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.homescreen.exercise_report.Activity
import kotlinx.coroutines.flow.Flow
@Dao
interface ActivityDAO {
    @Query("SELECT * FROM Activity")
    fun getAllActivities(): Flow<List<Activity>>

    @Query("SELECT name FROM ACTIVITY")
    fun getAllNames(): Flow<List<String>>

    @Insert
    suspend fun insertActivity(activity: Activity)
    @Update
    suspend fun updateActivity(activity: Activity)
    @Delete
    suspend fun deleteActivity(activity: Activity)
}