package com.priyanshu.stockinfo.data.local.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priyanshu.stockinfo.domain.models.TopGainerLoserItem

class Converters {
    @TypeConverter
    fun fromTopGainerLoserItemList(value: List<TopGainerLoserItem>): String {
        val gson = Gson()
        val type = object : TypeToken<List<TopGainerLoserItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTopGainerLoserItemList(value: String): List<TopGainerLoserItem> {
        val gson = Gson()
        val type = object : TypeToken<List<TopGainerLoserItem>>() {}.type
        return gson.fromJson(value, type)
    }
}