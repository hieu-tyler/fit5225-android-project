package com.example.homescreen.profile

import android.app.Application
import com.example.homescreen.AppDatabase
import kotlinx.coroutines.flow.Flow

class UserProfileRepository (application: Application) {
    private var userProfileDAO : UserProfileDAO =
        AppDatabase.getDatabase(application).userProfileDAO()
    val allUsers : Flow<List<UserProfile>> = userProfileDAO.getAllUsers()
    suspend fun insert(userProfile: UserProfile) {
        userProfileDAO.insertUser(userProfile)
    }
    suspend fun delete(userProfile: UserProfile) {
        userProfileDAO.deleteUser(userProfile)
    }
    suspend fun update(userProfile: UserProfile) {
        userProfileDAO.updateUser(userProfile)
    }
}