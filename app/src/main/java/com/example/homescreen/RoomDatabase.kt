package com.example.homescreen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homescreen.nutrition.FoodDAO
import com.example.homescreen.nutrition.Food

@Database(entities = [Food::class], version = 2, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}