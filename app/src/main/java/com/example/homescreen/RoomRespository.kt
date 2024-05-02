package com.example.homescreen

import android.app.Application
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.exercise_report.ActivityDAO
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food
import com.example.homescreen.health_metrics.UserHealthMetrics
import com.example.homescreen.health_metrics.UserHealthMetricsDAO
import kotlinx.coroutines.flow.Flow

class Repository(application: Application) {

    private var foodDao: FoodDAO =
        AppDatabase.getDatabase(application).foodDao()
    private var activityDAO : ActivityDAO =
        AppDatabase.getDatabase(application).activityDao()
    private var userHealthMetricsDAO: UserHealthMetricsDAO =
        AppDatabase.getDatabase(application).healthMetricsDao()
    val allActivities : Flow<List<Activity>> = activityDAO.getAllActivities()
    val allNames: Flow<List<String>> = activityDAO.getAllNames()

    val allFoods: Flow<List<Food>> = foodDao.getAllFoods()

    suspend fun insertFood(food: Food) {
        foodDao.insertFood(food)
    }

    suspend fun deleteFood(food: Food) {
        foodDao.deleteFood(food)
    }

    suspend fun updateFood(food: Food) {
        foodDao.updateFood(food)
    }

    suspend fun deleteAllFood() {
        foodDao.deleteAll()
    }


    suspend fun insertActivity(activity: Activity) {
        activityDAO.insertActivity(activity)
    }
    suspend fun deleteActivity(activity: Activity) {
        activityDAO.deleteActivity(activity)
    }
    suspend fun updateActivity(activity: Activity) {
        activityDAO.updateActivity(activity)
    }
    suspend fun insertUserHealthMetrics(metrics: UserHealthMetrics) {
        userHealthMetricsDAO.insertUserHealthMetrics(metrics)
    }

    suspend fun updateUserHealthMetrics(metrics: UserHealthMetrics) {
        userHealthMetricsDAO.updateUserHealthMetrics(metrics)
    }

    suspend fun deleteUserHealthMetrics(metrics: UserHealthMetrics) {
        userHealthMetricsDAO.deleteUserHealthMetrics(metrics)
    }
}