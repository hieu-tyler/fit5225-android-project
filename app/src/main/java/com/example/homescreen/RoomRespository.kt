package com.example.homescreen

import android.app.Application
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.exercise_report.ActivityDAO
import com.example.homescreen.exercise_report.UserActivity
import com.example.homescreen.exercise_report.UserActivityDAO
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food
import com.example.homescreen.nutrition.PersonalNutrition
import com.example.homescreen.nutrition.PersonalNutritionDAO
import com.example.homescreen.health_metrics.UserHealthMetrics
import com.example.homescreen.health_metrics.UserHealthMetricsDAO
import com.example.homescreen.nutrition.FoodRetrofit
import com.example.homescreen.nutrition.FoodSearchResponse
import com.example.homescreen.profile.UserProfile
import com.example.homescreen.profile.UserProfileDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Repository(application: Application) {

    private var foodDao: FoodDAO =
        AppDatabase.getDatabase(application).foodDao()
    private var activityDAO : ActivityDAO =
        AppDatabase.getDatabase(application).activityDao()
    private var userActivityDao : UserActivityDAO =
        AppDatabase.getDatabase(application).userActivityDao()
    private var personalNutritionDao : PersonalNutritionDAO =
        AppDatabase.getDatabase(application).personalNutritionDao()
    private var userHealthMetricsDAO: UserHealthMetricsDAO =
        AppDatabase.getDatabase(application).healthMetricsDao()
    private var userProfileDAO : UserProfileDAO =
        AppDatabase.getDatabase(application).userProfileDao()

    val allActivities : Flow<List<Activity>> = activityDAO.getAllActivities()
    val allNames: Flow<List<String>> = activityDAO.getAllNames()

    val allUserActivities : Flow<List<UserActivity>> = userActivityDao.getAllUserActivities()
    val allDistances: Flow<List<Float>> = userActivityDao.getAllDistance()


    val allFoods: Flow<List<Food>> = foodDao.getAllFoods()
    val allPersonalNutrition: Flow<List<PersonalNutrition>> = personalNutritionDao.getAllPersonalNutrition()
    private val searchService = FoodRetrofit.retrofitService
    private val API_KEY = "Here enter your API key"

    suspend fun getResponse(keyword: String): FoodSearchResponse {
        return searchService.getFoodFact(
            API_KEY,
            keyword
        )
    }
    val allUsers: Flow<List<UserProfile>> = userProfileDAO.getAllUsers()

    // Food
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
        foodDao.deleteAllFoods()
    }

    // PersonalNutrition
    suspend fun insertPersonalNutrition(personalNutrition: PersonalNutrition) {
        personalNutritionDao.insertPersonalNutrition(personalNutrition)
    }

    suspend fun deletePersonalNutrition(personalNutrition: PersonalNutrition) {
        personalNutritionDao.deletePersonalNutrition(personalNutrition)
    }

    suspend fun updatePersonalNutrition(personalNutrition: PersonalNutrition) {
        personalNutritionDao.updatePersonalNutrition(personalNutrition)
    }

    suspend fun deleteAllPersonalNutrition() {
        personalNutritionDao.deleteAllPersonalNutrition()
    }

    // Activity
    suspend fun getActivityId(activityName : String) : Int {
        return activityDAO.getActivityId(activityName)
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

    suspend fun deleteAllActivity() {
        activityDAO.deleteAllActivity()
    }

    //UserActivity

    suspend fun getUserActivities(userId : Int): Flow<List<UserActivity>> {
        return userActivityDao.getUserActivities(userId = userId)
    }
    suspend fun insertUserActivity(userActivity: UserActivity) {
        userActivityDao.insertUserActivity(userActivity)
    }


    suspend fun updateUserActivity(userActivity: UserActivity) {
        userActivityDao.updateUserActivity(userActivity)
    }

    suspend fun deleteUserActivity(userActivity: UserActivity) {
        userActivityDao.deleteUserActivity(userActivity)
    }


    suspend fun deleteAllUserActivity() {
        userActivityDao.deleteAllUserActivity()
    }

    // Health Metrics
    fun getUserHealthMetrics(userId: String): Flow<List<UserHealthMetrics>> {
        return userHealthMetricsDAO.getUserHealthMetrics(userId)
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

    // User profile
    suspend fun getUserProfile(userId: String): UserProfile? {
        return withContext(Dispatchers.IO) {
            userProfileDAO.getUserById(userId)
        }
    }
    suspend fun insertUser(userProfile: UserProfile) {
        userProfileDAO.insertUser(userProfile)
    }
    suspend fun deleteUser(userProfile: UserProfile) {
        userProfileDAO.deleteUser(userProfile)
    }
    suspend fun updateUser(userProfile: UserProfile) {
        userProfileDAO.updateUser(userProfile)
    }
}