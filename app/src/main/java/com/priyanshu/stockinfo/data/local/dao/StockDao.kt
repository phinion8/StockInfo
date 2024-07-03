package com.priyanshu.stockinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.priyanshu.stockinfo.data.local.entities.TopGainerLoserItem

@Dao
interface StockDao {

    @Upsert
    fun addAllTopGainersLosers(topGainerLoserItem: List<TopGainerLoserItem>)

    @Query("SELECT * FROM top_gainers_losers_table")
    fun getAllTopGainersLosers(): List<TopGainerLoserItem>

    @Query("DELETE FROM top_gainers_losers_table")
    fun clearAllTopGainersLosers()

}