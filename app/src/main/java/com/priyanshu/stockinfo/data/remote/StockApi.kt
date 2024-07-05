package com.priyanshu.stockinfo.data.remote

import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import okhttp3.ResponseBody
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

    @GET("query?function=TIME_SERIES_INTRADAY")
    suspend fun getIntraDayInfo(
        @Query("symbol") ticker: String,
        @Query("interval") interval: String,
        @Query("apikey") apiKey: String,
        @Query("datatype") datatype: String
    ): ResponseBody
}