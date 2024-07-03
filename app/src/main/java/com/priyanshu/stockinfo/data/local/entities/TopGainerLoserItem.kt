package com.priyanshu.stockinfo.data.local.entities

import androidx.room.Entity
import com.priyanshu.stockinfo.utils.Constants

@Entity(tableName = Constants.TOP_GAINERS_LOSERS_TABLE)
data class TopGainerLoserItem(
    val change_amount: String,
    val change_percentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)

