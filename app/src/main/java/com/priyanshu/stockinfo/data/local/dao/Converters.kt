package com.priyanshu.stockinfo.data.local.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priyanshu.stockinfo.domain.models.IntraDayInfo
import com.priyanshu.stockinfo.domain.models.IntraDayInfoEntity
import com.priyanshu.stockinfo.domain.models.TopGainerLoserItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    @TypeConverter
    fun fromIntraDayInfoList(value: List<IntraDayInfoEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<IntraDayInfoEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIntraDayInfoList(value: String): List<IntraDayInfoEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<IntraDayInfoEntity>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}
