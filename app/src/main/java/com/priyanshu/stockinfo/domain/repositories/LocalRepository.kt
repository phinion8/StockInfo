package com.priyanshu.stockinfo.domain.repositories

import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.IntraDayGraphEntity
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.waitlist.WaitlistEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun addTopGainersAndLosers(topGainerAndLosers: TopGainerAndLosers)
    suspend fun getTopGainersAndLosers(): TopGainerAndLosers
    suspend fun clearTopGainersLosers()
    suspend fun addSearchEntity(searchEntity: SearchEntity)
    suspend fun getSearchEntityList(): List<SearchEntity>
    suspend fun deleteFirstEntity()
    suspend fun addCompanyOverviewToLocal(companyOverview: CompanyOverview)
    suspend fun getCompanyOverviewFromLocal(symbol: String): CompanyOverview?
    suspend fun deleteCompanyOverviewFromLocal(symbol: String)
    suspend fun addWaitlistEntity(waitlistEntity: WaitlistEntity)
    suspend fun getWaitlistEntityList(): List<WaitlistEntity>
    suspend fun deleteWaitlistEntity(symbol: String)
    suspend fun getWaitlistEntity(symbol: String): WaitlistEntity?
    suspend fun isItemInWaitlist(symbol: String): Boolean
    suspend fun getSearchWaitlist(query: String): List<WaitlistEntity>
    suspend fun addIntraDayInfoGraphToLocal(intraDayGraphEntity: IntraDayGraphEntity)
    suspend fun getIntraDayInfoGraphFromLocal(symbol: String): IntraDayGraphEntity?
    suspend fun deleteIntraDayInfoGraphFromLocal(symbol: String)
}