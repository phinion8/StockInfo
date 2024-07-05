package com.priyanshu.stockinfo.domain.usecases

import android.util.Log
import coil.network.HttpException
import com.priyanshu.stockinfo.data.csv.CSVParser
import com.priyanshu.stockinfo.data.csv.IntraDayParser
import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.IntraDayInfo
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.search.SearchItem
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
    private val preferenceManager: PreferenceManager,
    private val intraDayParser: CSVParser<IntraDayInfo>
) {

    fun getTopGainersAndLosers(): Flow<Resource<TopGainerAndLosers>> = flow {
        emit(Resource.Loading())
        try {
            val cacheExpiry = Constants.CACHE_EXPIRY_IN_MINUTES * 60 * 1000
            preferenceManager.getCachedTime().collect { time ->
                if (System.currentTimeMillis() > time + cacheExpiry) {
                    val topGainersAndLosers = stockRepository.getTopGainersAndLosers()
                    localRepository.clearTopGainersLosers()
                    localRepository.addTopGainersAndLosers(topGainersAndLosers)
                    preferenceManager.saveCachedTime(System.currentTimeMillis())
                    emit(Resource.Success(topGainersAndLosers))
                } else {
                    val cachedLocalData = localRepository.getTopGainersAndLosers()
                    emit(Resource.Success(cachedLocalData))
                }
            }
        } catch (e: IOException) {
            val cachedLocalData = localRepository.getTopGainersAndLosers()
            emit(Resource.Success(cachedLocalData))
        } catch (e: HttpException) {
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

    fun getIntraDayInfoList(ticker: String): Flow<Resource<List<IntraDayInfo>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = stockRepository.getIntraDayInfo(ticker)
                Log.d("CSVRESULT", "response ${response.byteStream()}")
                val results = intraDayParser.parse(response.byteStream())
                Log.d("CSVRESULT", results.toString())
                emit(Resource.Success(results))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = e.localizedMessage
                    )
                )
            } catch (e: retrofit2.HttpException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = e.localizedMessage
                    )
                )
            }
        }.catch {
            it.printStackTrace()
            emit(
                Resource.Error(
                    message = it.localizedMessage
                )
            )

        }.flowOn(Dispatchers.IO)

    fun searchTicker(keyword: String): Flow<Resource<SearchItem>> = flow {
        emit(Resource.Loading())
        try {
            val response = stockRepository.searchTicker(keyword)
            if (response.Information != null) {
                emit(Resource.Error(response.Information))
            } else {
                emit(Resource.Success(response))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    message = e.localizedMessage
                )
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    message = e.localizedMessage
                )
            )
        }

    }.catch {
        it.printStackTrace()
        emit(
            Resource.Error(
                message = it.localizedMessage
            )
        )
    }.flowOn(Dispatchers.IO)

    suspend fun addSearchEntity(searchEntity: SearchEntity) {
        localRepository.addSearchEntity(searchEntity)
    }

    suspend fun getSearchEntityList(): Flow<Resource<List<SearchEntity>>> = flow {
        emit(Resource.Loading())
        try {

            val result = localRepository.getSearchEntityList()
            emit(Resource.Success(result))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }

    }.catch {
        it.printStackTrace()
        emit(Resource.Error(it.localizedMessage))
    }.flowOn(Dispatchers.IO)

    suspend fun deleteFirstSearchEntity(){
        localRepository.deleteFirstEntity()
    }




}