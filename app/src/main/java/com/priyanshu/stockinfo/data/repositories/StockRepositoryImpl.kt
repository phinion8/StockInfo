package com.priyanshu.stockinfo.data.repositories

import com.priyanshu.stockinfo.data.remote.StockApi
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.search.SearchItem
import com.priyanshu.stockinfo.domain.repositories.StockRepository
import com.priyanshu.stockinfo.utils.Constants
import okhttp3.ResponseBody
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi
) : StockRepository {
    override suspend fun getTopGainersAndLosers(): TopGainerAndLosers {
        return stockApi.getTopGainersAndLosers(apiKey = "demo")
    }

    override suspend fun getCompanyOverview(ticker: String): CompanyOverview? {
        return stockApi.getCompanyOverview(ticker, Constants.DEMO_API_KEY)
    }

    override suspend fun getIntraDayInfo(ticker: String): ResponseBody {
        return stockApi.getIntraDayInfo(ticker, "60min", Constants.DEMO_API_KEY, "csv")
    }

    override suspend fun searchTicker(keyword: String): SearchItem {
        return stockApi.searchTicker(keyword, Constants.DEMO_API_KEY)
    }
}