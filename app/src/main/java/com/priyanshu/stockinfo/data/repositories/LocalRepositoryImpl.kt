package com.priyanshu.stockinfo.data.repositories

import com.priyanshu.stockinfo.data.local.dao.StockDao
import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.repositories.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val stockDao: StockDao
) : LocalRepository {
    override suspend fun addTopGainersAndLosers(topGainerAndLosers: TopGainerAndLosers) {
        stockDao.addTopGainersAndLosers(topGainerAndLosers)
    }

    override suspend fun getTopGainersAndLosers(): TopGainerAndLosers {
        return stockDao.getTopGainersAnsLosers()
    }

    override suspend fun clearTopGainersLosers() {
        stockDao.clearAllTopGainersLosers()
    }

    override suspend fun addSearchEntity(searchEntity: SearchEntity) {
        stockDao.addSearchEntity(searchEntity)
    }

    override suspend fun getSearchEntityList(): List<SearchEntity> {
        return stockDao.getSearchEntityList()
    }

    override suspend fun deleteFirstEntity() {
        stockDao.deleteFirstItem()
    }

    override suspend fun addCompanyOverviewToLocal(companyOverview: CompanyOverview) {
        stockDao.addCompanyOverview(companyOverview)
    }

    override suspend fun getCompanyOverviewFromLocal(symbol: String): CompanyOverview? {
        return stockDao.getCompanyOverview(symbol)
    }

    override suspend fun deleteCompanyOverviewFromLocal(symbol: String) {
        stockDao.deleteCompanyOverview(symbol)
    }

}