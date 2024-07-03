package com.priyanshu.stockinfo.data.repositories

import com.priyanshu.stockinfo.data.remote.StockApi
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.repositories.StockRepository
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi
): StockRepository {
    override suspend fun getTopGainersAndLosers(): TopGainerAndLosers {
        return stockApi.getTopGainersAndLosers()
    }
}