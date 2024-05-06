package com.example.homescreen

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.exercise_report.UserActivity
import com.example.homescreen.health_metrics.UserHealthMetrics
import com.example.homescreen.nutrition.Food
import com.example.homescreen.nutrition.FoodAPI
import com.example.homescreen.nutrition.PersonalNutrition
import com.example.homescreen.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    init {
        repository = Repository(application)
    }

    // Reset password
    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    val allFoods: LiveData<List<Food>> = repository.allFoods.asLiveData()
    val allPersonalNutrition: LiveData<List<PersonalNutrition>> = repository.allPersonalNutrition.asLiveData()
    val retrofitResponse: MutableState<List<FoodAPI>> = mutableStateOf((emptyList()))

    // Activity
    val allActivities: LiveData<List<Activity>> = repository.allActivities.asLiveData()
    val allNames: LiveData<List<String>> = repository.allNames.asLiveData()

    // User_Activity
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: MutableLiveData<UserProfile> = _userProfile
    val allUsers: LiveData<List<UserProfile>> = repository.allUsers.asLiveData()

    val allUserActivities: LiveData<List<UserActivity>> = repository.allUserActivities.asLiveData()
    val allDistances: LiveData<List<Float>> = repository.allDistances.asLiveData()

    // Reset password
    fun sendPasswordResetEmail(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _statusMessage.postValue("Reset link sent to your email")
                } else {
                    _statusMessage.postValue("Failed to send reset link: ${task.exception?.localizedMessage}")
                }
            }
    }

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

    fun updatePersonalNutrition(personalNutrition: PersonalNutrition) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePersonalNutrition(personalNutrition)
    }
    fun deletePersonalNutrition(personalNutrition: PersonalNutrition) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePersonalNutrition(personalNutrition)
    }
    fun deleteAllPersonalNutrition() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllPersonalNutrition()
    }

    fun getResponse(keyword:String) {
        viewModelScope.launch  {
            try {
                val responseReturned = repository.getResponse(keyword)
                Log.i("Response", "Response : $responseReturned")
                retrofitResponse.value = responseReturned

            } catch (e: Exception) {
                Log.i("Error ", "Response failed : $e")
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

    // Health Metrics
    fun getUserHealthMetrics(userId: String): LiveData<List<UserHealthMetrics>> {
        return repository.getUserHealthMetrics(userId).asLiveData()
    }

    fun insertUserHealthMetrics(metrics: UserHealthMetrics) = viewModelScope.launch(Dispatchers.IO) {
        val newId = repository.insertUserHealthMetrics(metrics)
        Log.d("ViewModel", "New record ID: $newId")
    }

    fun updateUserHealthMetrics(metrics: UserHealthMetrics) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUserHealthMetrics(metrics)
    }

    fun deleteUserHealthMetrics(metrics: UserHealthMetrics) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUserHealthMetrics(metrics)
    }

    // User Profile
    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            val profile = repository.getUserProfile(userId)
            _userProfile.value = profile!!
        }
    }
    fun insertUser(userProfile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser(userProfile)
    }
    fun updateUser(userProfile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(userProfile)
    }
    fun deleteUser(userProfile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUser(userProfile)
    }
}