package com.priyanshu.stockinfo.data.repositories

import com.priyanshu.stockinfo.data.remote.StockApi
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.search.SearchItem
import com.priyanshu.stockinfo.domain.repositories.StockRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi
) : StockRepository {
    override suspend fun getTopGainersAndLosers(): TopGainerAndLosers {
        return stockApi.getTopGainersAndLosers()
    }

    override suspend fun getCompanyOverview(ticker: String): CompanyOverview? {
        return stockApi.getCompanyOverview(ticker, "demo")
    }

    override suspend fun getIntraDayInfo(ticker: String): ResponseBody {
        return stockApi.getIntraDayInfo(ticker, "60min", "RB17JB0AWDKNAB70", "csv")
    }

    override suspend fun searchTicker(keyword: String): SearchItem {
        return stockApi.searchTicker(keyword, "demo")
    }
}