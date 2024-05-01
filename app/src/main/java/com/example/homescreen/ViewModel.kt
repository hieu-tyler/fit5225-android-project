package com.example.homescreen

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.nutrition.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    init {
        repository = Repository(application)
    }
    val allFoods: LiveData<List<Food>> =  repository.allFoods.asLiveData()

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

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllFood()
    }
    val allActivities: LiveData<List<Activity>> = repository.allActivities.asLiveData()
    val allNames: LiveData<List<String>> = repository.allNames.asLiveData()
    fun insertActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertActivity(activity)
    }
    fun updateActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateActivity(activity)
    }
    fun deleteActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteActivity(activity)
    }
}