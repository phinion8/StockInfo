package com.priyanshu.stockinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopGainersAndLosers(topGainerAndLosers: TopGainerAndLosers)

    @Query("SELECT * FROM top_gainers_losers_table")
    suspend fun getTopGainersAnsLosers(): TopGainerAndLosers

    @Query("DELETE FROM top_gainers_losers_table")
    suspend fun clearAllTopGainersLosers()

}