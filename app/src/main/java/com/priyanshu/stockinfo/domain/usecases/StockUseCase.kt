package com.priyanshu.stockinfo.domain.usecases

import android.util.Log
import coil.network.HttpException
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.repositories.LocalRepository
import com.priyanshu.stockinfo.domain.repositories.PreferenceManager
import com.priyanshu.stockinfo.domain.repositories.StockRepository
import com.priyanshu.stockinfo.navigation.Screens
import com.priyanshu.stockinfo.utils.Constants
import com.priyanshu.stockinfo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import java.io.IOException
import javax.inject.Inject

class StockUseCase @Inject constructor(
    private val stockRepository: StockRepository,
    private val localRepository: LocalRepository,
    private val preferenceManager: PreferenceManager
) {

    fun getTopGainersAndLosers(): Flow<Resource<TopGainerAndLosers>> = flow {
        emit(Resource.Loading())
        try {
            val cacheExpiry = Constants.CACHE_EXPIRY_IN_MINUTES * 60 * 1000
            preferenceManager.getCachedTime().collect{time->
                if (System.currentTimeMillis() > time + cacheExpiry){
                    val topGainersAndLosers = stockRepository.getTopGainersAndLosers()
                    localRepository.clearTopGainersLosers()
                    localRepository.addTopGainersAndLosers(topGainersAndLosers)
                    preferenceManager.saveCachedTime(System.currentTimeMillis())
                    emit(Resource.Success(topGainersAndLosers))
                }else{
                    val cachedLocalData = localRepository.getTopGainersAndLosers()
                    emit(Resource.Success(cachedLocalData))
                }
            }
        } catch (e: IOException) {
            val cachedLocalData = localRepository.getTopGainersAndLosers()
            emit(Resource.Success(cachedLocalData))
        } catch (e: HttpException){
            val cachedLocalData = localRepository.getTopGainersAndLosers()
            emit(Resource.Success(cachedLocalData))
        }
    }.catch {
        emit(Resource.Error(it.message))
    }.flowOn(Dispatchers.IO)

    fun getCompanyOverview(ticker: String): Flow<Resource<CompanyOverview?>> = flow {
        emit(Resource.Loading())
        try {
            val companyOverview = stockRepository.getCompanyOverview(ticker)
            emit(Resource.Success(companyOverview))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        }
    }.catch {
        emit(Resource.Error(it.message))
    }.flowOn(Dispatchers.IO)

}