package com.priyanshu.stockinfo.data.repositories

import com.priyanshu.stockinfo.data.local.dao.StockDao
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.repositories.LocalRepository
import kotlinx.coroutines.flow.Flow
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

}