package com.example.homescreen

import android.app.Application
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.exercise_report.ActivityDAO
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food
import kotlinx.coroutines.flow.Flow

class Repository(application: Application) {

    private var foodDao: FoodDAO =
        AppDatabase.getDatabase(application).foodDao()
    private var activityDAO : ActivityDAO =
        AppDatabase.getDatabase(application).activityDao()
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
}