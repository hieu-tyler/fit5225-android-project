package com.example.homescreen.exercise_report

import android.app.Application
import kotlinx.coroutines.flow.Flow

class ActivityRepository (application: Application) {
    private var activityDAO : ActivityDAO =
        ActivityDatabase.getDatabase(application).activityDAO()
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