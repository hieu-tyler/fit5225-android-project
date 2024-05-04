package com.example.homescreen

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.exercise_report.UserActivity
import com.example.homescreen.health_metrics.UserHealthMetrics
import com.example.homescreen.nutrition.Food
import com.example.homescreen.nutrition.PersonalNutrition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    init {
        repository = Repository(application)
    }
    val allFoods: LiveData<List<Food>> = repository.allFoods.asLiveData()
    val allPersonalNutrition: LiveData<List<PersonalNutrition>> = repository.allPersonalNutrition.asLiveData()

    //Activity
    val allActivities: LiveData<List<Activity>> = repository.allActivities.asLiveData()
    val allNames: LiveData<List<String>> = repository.allNames.asLiveData()

    //User_Activity
    val allUserActivities: LiveData<List<UserActivity>> = repository.allUserActivities.asLiveData()
    val allDistances: LiveData<List<Float>> = repository.allDistances.asLiveData()


    fun insertFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFood(food)
    }

    fun insertFoods(foodList: List<Food>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (food in foodList) {
                repository.insertFood(food)
                Log.d(ContentValues.TAG, "Inserted food: ${food.name}")
            }
        }
    }
    fun updateFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateFood(food)
    }

    fun deleteFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFood(food)
    }

    // Personal Nutrition
    fun insertPersonalNutrition(personalNutrition: PersonalNutrition) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertPersonalNutrition(personalNutrition)
    }
    fun insertPersonalNutritions(personalNutritionList: List<PersonalNutrition>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (personalNutrition in personalNutritionList) {
                repository.insertPersonalNutrition(personalNutrition)
                Log.d(ContentValues.TAG, "Inserted personalNutrition food: ${personalNutrition.foodName}")
            }
        }
    }
    fun updatePersonalNutrition(personalNutrition: PersonalNutrition) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePersonalNutrition(personalNutrition)
    }
    fun deletePersonalNutrition(personalNutrition: PersonalNutrition) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePersonalNutrition(personalNutrition)
    }
    fun deleteAllPersonalNutrition() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllPersonalNutrition()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun savePersonalNutrition(personalNutritionList: List<PersonalNutrition>) = viewModelScope.launch(Dispatchers.IO) {
        val currentDate = LocalDate.now().toString()
        val todayNutritionRecords = allPersonalNutrition.value?.filter { it.date == currentDate }
        for (personalNutrition in personalNutritionList) {
            var existingRecord = todayNutritionRecords?.firstOrNull {
                it.userName == personalNutrition.userName &&
                        it.date == personalNutrition.date &&
                        it.category == personalNutrition.category &&
                        it.foodName == personalNutrition.foodName
            }

            if (existingRecord != null) {
                // If a record exists, update its quantity
                existingRecord.quantity += personalNutrition.quantity
                // Update the record in the database
                updatePersonalNutrition(existingRecord)
            } else {
                // If no record exists, insert a new record
                insertPersonalNutrition(personalNutrition)
            }
        }
    }

    // Activity
    fun getActivityId(activityName: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getActivityId(activityName)
    }
    fun insertActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertActivity(activity)
    }
    fun updateActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateActivity(activity)
    }
    fun deleteActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteActivity(activity)
    }
    fun deleteAllActivity() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllActivity()
    }

    //UserActivity
    fun getUserActivities(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getUserActivities(userId)
    }
    fun insertUserActivity(userActivity: UserActivity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUserActivity(userActivity)
    }

    fun updateUserActivity(userActivity: UserActivity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUserActivity(userActivity)
    }

    fun deleteUserActivity(userActivity: UserActivity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUserActivity(userActivity)
    }

    fun deleteAllUserActivity() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllUserActivity()
    }

    fun insertUserHealthMetrics(metrics: UserHealthMetrics) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUserHealthMetrics(metrics)
    }

    fun updateUserHealthMetrics(metrics: UserHealthMetrics) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUserHealthMetrics(metrics)
    }

    fun deleteUserHealthMetrics(metrics: UserHealthMetrics) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUserHealthMetrics(metrics)
    }
}