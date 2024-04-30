package com.example.homescreen

import android.app.Application
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food
import kotlinx.coroutines.flow.Flow

class FoodRepository (application: Application) {

    private var foodDao: FoodDAO =
        FoodDatabase.getDatabase(application).foodDao()

    val allFoods: Flow<List<Food>> = foodDao.getAllFoods()

    suspend fun insert(food: Food) {
        foodDao.insertFood(food)
    }

    suspend fun delete(food: Food) {
        foodDao.deleteFood(food)
    }

    suspend fun update(food: Food) {
        foodDao.updateFood(food)
    }
}