package com.example.homescreen.nutrition

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.homescreen.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val cRepository: FoodRepository

    init {
        cRepository = FoodRepository(application)
    }
    val allFoods: LiveData<List<Food>> =  cRepository.allFoods.asLiveData()

    fun insertFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(food)
    }

    fun insertFoods(foodList: List<Food>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (food in foodList) {
                cRepository.insert(food)
                Log.d(TAG, "Inserted food: ${food.name}")
            }
        }
    }
    fun updateFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(food)
    }

    fun deleteFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(food)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        cRepository.deleteAll()
    }
}