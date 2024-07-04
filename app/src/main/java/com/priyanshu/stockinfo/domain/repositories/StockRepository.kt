package com.priyanshu.stockinfo.domain.repositories

import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers

interface StockRepository {
    suspend fun getTopGainersAndLosers(): TopGainerAndLosers
    suspend fun getCompanyOverview(ticker: String): CompanyOverview?
}