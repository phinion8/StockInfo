package com.priyanshu.stockinfo.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.priyanshu.stockinfo.data.local.dao.Converters
import com.priyanshu.stockinfo.utils.Constants

@Entity(tableName = Constants.TOP_GAINERS_LOSERS_TABLE)
data class TopGainerAndLosers(
    @PrimaryKey val last_updated: String,
    val metadata: String,
    @TypeConverters(Converters::class) val most_actively_traded: List<TopGainerLoserItem>,
    @TypeConverters(Converters::class) val top_gainers: List<TopGainerLoserItem>,
    @TypeConverters(Converters::class) val top_losers: List<TopGainerLoserItem>
)

@Entity(tableName = Constants.TOP_GAINERS_LOSERS_ITEM_TABLE)
data class TopGainerLoserItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val change_amount: String,
    val change_percentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)