package com.example.homescreen.exercise_report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class ActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val cRepository: ActivityRepository
    init{
        cRepository = ActivityRepository(application)
    }
    val allActivities: LiveData<List<Activity>> = cRepository.allActivities.asLiveData()
    val allNames: LiveData<List<String>> = cRepository.allNames.asLiveData()
    fun insertActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(activity)
    }
    fun updateActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(activity)
    }
    fun deleteActivity(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(activity)
    }
}