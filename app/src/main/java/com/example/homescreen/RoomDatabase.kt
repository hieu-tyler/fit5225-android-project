package com.example.homescreen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food
import com.example.homescreen.profile.Converter
import com.example.homescreen.profile.UserProfile
import com.example.homescreen.profile.UserProfileDAO

@Database(entities = [Food::class, UserProfile::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    abstract fun userProfileDAO(): UserProfileDAO

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