package com.example.homescreen.exercise_report

import android.app.Application
import com.example.homescreen.AppDatabase
import kotlinx.coroutines.flow.Flow

class ActivityRepository (application: Application) {
    private var activityDAO : ActivityDAO =
        AppDatabase.getDatabase(application).activityDao()
    val allActivities : Flow<List<Activity>> = activityDAO.getAllActivities()
    val allNames: Flow<List<String>> = activityDAO.getAllNames()
    suspend fun insert(activity: Activity) {
        activityDAO.insertActivity(activity)
    }
    suspend fun delete(activity: Activity) {
        activityDAO.deleteActivity(activity)
    }
    suspend fun update(activity: Activity) {
        activityDAO.updateActivity(activity)
    }
}