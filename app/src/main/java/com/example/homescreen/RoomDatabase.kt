package com.example.homescreen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homescreen.exercise_report.Activity
import com.example.homescreen.exercise_report.ActivityDAO
import com.example.homescreen.exercise_report.UserActivity
import com.example.homescreen.exercise_report.UserActivityDAO
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food
import com.example.homescreen.nutrition.PersonalNutrition
import com.example.homescreen.nutrition.PersonalNutritionDAO
import com.example.homescreen.profile.Converter
import com.example.homescreen.profile.UserProfile
import com.example.homescreen.profile.UserProfileDAO

@Database(
    entities = [
        Food::class,
        Activity::class,
        UserProfile::class,
        PersonalNutrition::class,
        UserActivity::class
               ],
    version = 5,
    exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO

    abstract fun personalNutritionDao() : PersonalNutritionDAO

    abstract fun activityDao() : ActivityDAO
    abstract fun userActivityDao() : UserActivityDAO

    abstract fun userProfileDao(): UserProfileDAO

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