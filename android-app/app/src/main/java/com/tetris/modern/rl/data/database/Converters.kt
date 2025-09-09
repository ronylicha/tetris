package com.tetris.modern.rl.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return value?.let { gson.fromJson(it, listType) }
    }
    
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun fromIntList(value: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return value?.let { gson.fromJson(it, listType) }
    }
    
    @TypeConverter
    fun fromIntListToString(list: List<Int>?): String? {
        return list?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun fromMap(value: String?): Map<String, Any>? {
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        return value?.let { gson.fromJson(it, mapType) }
    }
    
    @TypeConverter
    fun fromMapToString(map: Map<String, Any>?): String? {
        return map?.let { gson.toJson(it) }
    }
}