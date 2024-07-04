package com.priyanshu.stockinfo.data.remote

import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=TOP_GAINERS_LOSERS&apikey=demo")
    suspend fun getTopGainersAndLosers(): TopGainerAndLosers

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyOverview(
        @Query("symbol") ticker: String,
        @Query("apikey") apiKey: String
    ): CompanyOverview?
}