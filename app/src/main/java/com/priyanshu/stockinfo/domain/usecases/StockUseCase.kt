package com.priyanshu.stockinfo.domain.usecases

import android.util.Log
import coil.network.HttpException
import com.google.gson.Gson
import com.priyanshu.stockinfo.data.csv.CSVParser
import com.priyanshu.stockinfo.data.csv.IntraDayParser
import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.IntraDayGraphEntity
import com.priyanshu.stockinfo.domain.models.IntraDayInfo
import com.priyanshu.stockinfo.domain.models.IntraDayInfoEntity
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.search.SearchItem
import com.priyanshu.stockinfo.domain.models.waitlist.WaitlistEntity
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
import org.json.JSONObject
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
            val cacheExpiry = Constants.CACHE_EXPIRY_IN_MINUTES * 60 * 1000

            val companyOverviewFromLocal = localRepository.getCompanyOverviewFromLocal(ticker)

            val information = companyOverviewFromLocal?.Information

            if (companyOverviewFromLocal != null && System.currentTimeMillis() > companyOverviewFromLocal.localCreationTime + cacheExpiry) {
                val companyOverview = stockRepository.getCompanyOverview(ticker)
                if (companyOverview != null) {
                    localRepository.deleteCompanyOverviewFromLocal(companyOverview.Symbol)
                    localRepository.addCompanyOverviewToLocal(companyOverview)
                    val overViewLocal = localRepository.getCompanyOverviewFromLocal(ticker)
                    emit(Resource.Success(overViewLocal))
                } else if (information != null) {
                    emit(Resource.Error(information))
                } else {
                    emit(Resource.Error("Something went wrong"))
                }
            } else if (companyOverviewFromLocal == null) {
                Log.d("gerComanyOverview", "company overview from local is null")
                val companyOverview = stockRepository.getCompanyOverview(ticker)
                Log.d("gerComanyOverview", "calling api")
                if (companyOverview != null && companyOverview.Information == null) {
                    localRepository.addCompanyOverviewToLocal(companyOverview)
                    val overViewLocal = localRepository.getCompanyOverviewFromLocal(ticker)
                    emit(Resource.Success(overViewLocal))
                } else if (companyOverview?.Information != null) {
                    emit(Resource.Error(companyOverview.Information))
                } else {
                    emit(Resource.Error("Something went wrong"))
                }

            } else {
                val overViewLocal = localRepository.getCompanyOverviewFromLocal(ticker)
                emit(Resource.Success(overViewLocal))
            }

        } catch (e: IOException) {
            val overViewLocal = localRepository.getCompanyOverviewFromLocal(ticker)
            emit(Resource.Success(overViewLocal))
        } catch (e: HttpException) {
            val overViewLocal = localRepository.getCompanyOverviewFromLocal(ticker)
            emit(Resource.Success(overViewLocal))
        }
    }.catch {
        emit(Resource.Error(it.message))
    }.flowOn(Dispatchers.IO)

    fun getIntraDayInfoList(ticker: String): Flow<Resource<List<IntraDayInfoEntity>>> =
        flow {
            emit(Resource.Loading())
            try {

                val cacheExpiry = Constants.CACHE_EXPIRY_IN_MINUTES * 60 * 1000
                val intraDayInfoFromLocal = localRepository.getIntraDayInfoGraphFromLocal(ticker)

                if (intraDayInfoFromLocal == null) {
                    Log.d("SOMEISSUE", "local is null")
                    val response = stockRepository.getIntraDayInfo(ticker)
                    val results = intraDayParser.parse(response.byteStream())
                    val intraDayInfoEntityList = ArrayList<IntraDayInfoEntity>()
                    results.map {
                        intraDayInfoEntityList.add(
                            IntraDayInfoEntity(
                                hour = it.date.hour,
                                close = it.close
                            )
                        )
                    }
                    if (results.isNotEmpty()){
                        localRepository.addIntraDayInfoGraphToLocal(
                            IntraDayGraphEntity(
                                symbol = ticker,
                                intraDayInfo = intraDayInfoEntityList,
                                createdTime = System.currentTimeMillis()
                            )
                        )
                        val infoFromLocal = localRepository.getIntraDayInfoGraphFromLocal(ticker)
                        if (infoFromLocal != null) {
                            emit(Resource.Success(infoFromLocal.intraDayInfo))
                        }else{
                            emit(Resource.Error("Something went wrong!"))
                        }
                    }else{
                        emit(Resource.Error("Graph not available."))
                    }

                } else if (intraDayInfoFromLocal != null && System.currentTimeMillis() > intraDayInfoFromLocal.createdTime + cacheExpiry) {
                    Log.d("SOMEISSUE", "local is not null but expired")
                    val response = stockRepository.getIntraDayInfo(ticker)
                    val results = intraDayParser.parse(response.byteStream())
                    val intraDayInfoEntityList = ArrayList<IntraDayInfoEntity>()
                    results.map {
                        intraDayInfoEntityList.add(
                            IntraDayInfoEntity(
                                hour = it.date.hour,
                                close = it.close
                            )
                        )
                    }
                    if (results.isNotEmpty()){
                        localRepository.addIntraDayInfoGraphToLocal(
                            IntraDayGraphEntity(
                                symbol = ticker,
                                intraDayInfo = intraDayInfoEntityList,
                                createdTime = System.currentTimeMillis()
                            )
                        )
                        val infoFromLocal = localRepository.getIntraDayInfoGraphFromLocal(ticker)
                        if (infoFromLocal != null) {
                            emit(Resource.Success(infoFromLocal.intraDayInfo))
                        }
                    }else{
                        emit(Resource.Error("Can not load graph at the moment, Please try again after some time."))
                    }

                } else {
                    Log.d("SOMEISSUE", "local is not null")
                    val infoFromLocal = localRepository.getIntraDayInfoGraphFromLocal(ticker)
                    if (infoFromLocal != null) {
                        emit(Resource.Success(infoFromLocal.intraDayInfo))
                    }
                }

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

    suspend fun getWaitlistEntityList(): Flow<Resource<List<WaitlistEntity>>> = flow {
        emit(Resource.Loading())
        try {

            val result = localRepository.getWaitlistEntityList()
            emit(Resource.Success(result))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }

    }.catch {
        it.printStackTrace()
        emit(Resource.Error(it.localizedMessage))
    }.flowOn(Dispatchers.IO)

    suspend fun searchWaitlist(query: String): Flow<Resource<List<WaitlistEntity>>> = flow {
        emit(Resource.Loading())
        try {

            val result = localRepository.getSearchWaitlist(query)
            emit(Resource.Success(result))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }

    }.catch {
        it.printStackTrace()
        emit(Resource.Error(it.localizedMessage))
    }.flowOn(Dispatchers.IO)

    suspend fun deleteFirstSearchEntity() {
        localRepository.deleteFirstEntity()
    }

    suspend fun addWaitListEntity(waitlistEntity: WaitlistEntity) {
        localRepository.addWaitlistEntity(waitlistEntity)
    }

    suspend fun deleteWaitlistEntity(symbol: String) {
        localRepository.deleteWaitlistEntity(symbol)
    }

    fun isItemInWaitlist(symbol: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {

            val result = localRepository.isItemInWaitlist(symbol)
            emit(Resource.Success(result))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }

    }.catch {
        it.printStackTrace()
        emit(Resource.Error(it.localizedMessage))
    }.flowOn(Dispatchers.IO)


}