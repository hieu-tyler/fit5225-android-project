package com.example.homescreen.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val cRepository: UserProfileRepository
    init{
        cRepository = UserProfileRepository(application)
    }
    val allUsers: LiveData<List<UserProfile>> = cRepository.allUsers.asLiveData()
    fun insertUser(userProfile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(userProfile)
    }
    fun updateUser(userProfile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(userProfile)
    }
    fun deleteUser(userProfile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(userProfile)
    }
}