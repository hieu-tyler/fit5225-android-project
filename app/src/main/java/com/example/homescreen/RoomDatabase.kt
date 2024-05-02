package com.example.homescreen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.exercise_report.ActivityDAO
import com.example.homescreen.health_metrics.UserHealthMetrics
import com.example.homescreen.health_metrics.UserHealthMetricsDAO
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food
import com.example.homescreen.nutrition.PersonalNutrition
import com.example.homescreen.nutrition.PersonalNutritionDAO
import com.example.homescreen.profile.Converter
import com.example.homescreen.profile.UserProfile
import com.example.homescreen.profile.UserProfileDAO

@Database(entities = [Food::class, Activity::class, UserProfile::class, UserHealthMetrics::class , PersonalNutrition::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO

    abstract fun personalNutritionDao() : PersonalNutritionDAO


    abstract fun activityDao() : ActivityDAO

    abstract fun userProfileDao(): UserProfileDAO

    abstract fun healthMetricsDao(): UserHealthMetricsDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}