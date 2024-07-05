package com.priyanshu.stockinfo.domain.repositories

import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
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
}