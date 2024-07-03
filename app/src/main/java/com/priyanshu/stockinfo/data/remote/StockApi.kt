package com.priyanshu.stockinfo.data.remote

import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import retrofit2.http.GET

interface StockApi {
    @GET("query?function=TOP_GAINERS_LOSERS&apikey=demo")
    suspend fun getTopGainersAndLosers(): TopGainerAndLosers
}