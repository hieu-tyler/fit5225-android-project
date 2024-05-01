package com.example.homescreen.nutrition

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface PersonalNutritionDAO {
    @Query("SELECT * FROM personal_nutrition")
    fun getAllPersonalNutrition(): Flow<List<PersonalNutrition>>

    @Insert
    suspend fun insertPersonalNutrition(personalNutrition: PersonalNutrition)

    @Update
    suspend fun updatePersonalNutrition(personalNutrition: PersonalNutrition)

    @Delete
    suspend fun deletePersonalNutrition(personalNutrition: PersonalNutrition)

    @Query("DELETE FROM personal_nutrition")
    suspend fun deleteAllPersonalNutrition()

    @Query("SELECT \n" +
            "    userName,\n" +
            "    date,\n" +
            "    foodName,\n" +
            "    category,\n" +
            "    SUM(quantity) AS totalQuantity,\n" +
            "    SUM(carbs) AS totalCarbs,\n" +
            "    SUM(protein) AS totalProtein,\n" +
            "    SUM(fats) AS totalFats\n" +
            "FROM personal_nutrition\n" +
            "GROUP BY userName, date, foodName, category;")
    fun getNutritionFacts(): Flow<List<PersonalNutrition>>
}