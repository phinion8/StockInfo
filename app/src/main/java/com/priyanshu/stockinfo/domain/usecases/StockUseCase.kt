package com.priyanshu.stockinfo.domain.usecases

import android.util.Log
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.repositories.StockRepository
import com.priyanshu.stockinfo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StockUseCase @Inject constructor(
    private val stockRepository: StockRepository
) {

    fun getTopGainersAndLosers(): Flow<Resource<TopGainerAndLosers>> = flow {
        emit(Resource.Loading())
        try {
            val topGainersAndLosers = stockRepository.getTopGainersAndLosers()
            emit(Resource.Success(topGainersAndLosers))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }.catch {
        emit(Resource.Error(it.message))
    }.flowOn(Dispatchers.IO)

}