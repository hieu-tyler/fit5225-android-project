package com.example.homescreen.nutrition

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDAO {
    @Query("SELECT * FROM foods")
    fun getAllFoods(): Flow<List<Food>>

    @Insert
    suspend fun insertFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("DELETE FROM foods")
    suspend fun deleteAllFoods()
}