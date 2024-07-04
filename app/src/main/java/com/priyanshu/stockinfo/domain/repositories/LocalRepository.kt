package com.priyanshu.stockinfo.domain.repositories

import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun addTopGainersAndLosers(topGainerAndLosers: TopGainerAndLosers)
    suspend fun getTopGainersAndLosers(): TopGainerAndLosers
    suspend fun clearTopGainersLosers()
}