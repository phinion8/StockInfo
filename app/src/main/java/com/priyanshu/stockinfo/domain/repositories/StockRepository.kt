package com.priyanshu.stockinfo.domain.repositories

import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import okhttp3.ResponseBody

interface StockRepository {
    suspend fun getTopGainersAndLosers(): TopGainerAndLosers
    suspend fun getCompanyOverview(ticker: String): CompanyOverview?
    suspend fun getIntraDayInfo(ticker: String): ResponseBody
}