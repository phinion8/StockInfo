package com.priyanshu.stockinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.IntraDayGraphEntity
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.waitlist.WaitlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopGainersAndLosers(topGainerAndLosers: TopGainerAndLosers)

    @Query("SELECT * FROM top_gainers_losers_table")
    suspend fun getTopGainersAnsLosers(): TopGainerAndLosers

    @Query("DELETE FROM top_gainers_losers_table")
    suspend fun clearAllTopGainersLosers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchEntity(searchEntity: SearchEntity)

    @Query("SELECT * FROM search_entity_table ORDER BY createdAt DESC")
    suspend fun getSearchEntityList(): List<SearchEntity>

    @Query("DELETE FROM search_entity_table WHERE id = (SELECT id FROM search_entity_table ORDER BY createdAt LIMIT 1)")
    suspend fun deleteFirstItem()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompanyOverview(companyOverview: CompanyOverview)

    @Query("SELECT * FROM company_overview_table WHERE symbol = :symbol LIMIT 1")
    suspend fun getCompanyOverview(symbol: String): CompanyOverview?

    @Query("DELETE FROM company_overview_table WHERE symbol = :symbol")
    suspend fun deleteCompanyOverview(symbol: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWaitlistEntity(waitlistEntity: WaitlistEntity)

    @Query("SELECT * FROM waitlist_table")
    suspend fun getWaitlistEntityList(): List<WaitlistEntity>

    @Query("DELETE FROM waitlist_table WHERE symbol = :symbol")
    suspend fun deleteWaitlistEntity(symbol: String)

    @Query("SELECT * FROM waitlist_table WHERE symbol = :symbol LIMIT 1")
    suspend fun getWaitlistEntity(symbol: String): WaitlistEntity?

    @Query("SELECT EXISTS (SELECT 1 FROM waitlist_table WHERE symbol = :symbol)")
    fun isItemInWaitlist(symbol: String): Boolean

    @Query("SELECT * FROM waitlist_table WHERE name LIKE '%' || :searchQuery || '%' OR symbol LIKE '%' || :searchQuery || '%' OR type LIKE '%' || :searchQuery || '%'")
    fun searchWaitlist(searchQuery: String): List<WaitlistEntity>

    @Insert
    fun addIntraDayInfoGraphToLocal(intraDayGraphEntity: IntraDayGraphEntity)

    @Query("SELECT * FROM intraday_graph_table WHERE symbol = :symbol")
    fun getIntraDayInfoGraphFromLocal(symbol: String): IntraDayGraphEntity?

    @Query("DELETE FROM intraday_graph_table WHERE symbol = :symbol")
    fun deleteIntraDayInfoGraphFromLocal(symbol: String)



}