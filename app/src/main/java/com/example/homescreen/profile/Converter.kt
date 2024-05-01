package com.example.homescreen.profile

import androidx.room.TypeConverter
import java.util.Date

class Converter {
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}