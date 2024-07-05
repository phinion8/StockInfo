package com.priyanshu.stockinfo.di

import android.content.Context
import androidx.room.Room
import com.priyanshu.stockinfo.data.csv.CSVParser
import com.priyanshu.stockinfo.data.csv.IntraDayParser
import com.priyanshu.stockinfo.data.local.dao.StockDao
import com.priyanshu.stockinfo.data.local.database.StockDatabase
import com.priyanshu.stockinfo.data.remote.StockApi
import com.priyanshu.stockinfo.data.repositories.LocalRepositoryImpl
import com.priyanshu.stockinfo.data.repositories.PreferenceManagerImpl
import com.priyanshu.stockinfo.data.repositories.StockRepositoryImpl
import com.priyanshu.stockinfo.domain.models.IntraDayInfo
import com.priyanshu.stockinfo.domain.repositories.LocalRepository
import com.priyanshu.stockinfo.domain.repositories.PreferenceManager
import com.priyanshu.stockinfo.domain.repositories.StockRepository
import com.priyanshu.stockinfo.domain.usecases.StockUseCase
import com.priyanshu.stockinfo.utils.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(StockApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, StockDatabase::class.java, Constants.STOCK_DATABASE)
        .build()

    @Provides
    @Singleton
    fun provideStockDao(
        stockDatabase: StockDatabase
    ) = stockDatabase.stockDao()

    @Provides
    @Singleton
    fun provideLocalRepository(
        stockDao: StockDao
    ): LocalRepository = LocalRepositoryImpl(stockDao)

    @Provides
    @Singleton
    fun provideStockRepository(
        stockApi: StockApi
    ): StockRepository {
        return StockRepositoryImpl(stockApi)
    }

    @Provides
    @Singleton
    fun provideStockUseCase(
        stockRepository: StockRepository,
        localRepository: LocalRepository,
        preferenceManager: PreferenceManager,
        intaDayParser: IntraDayParser
    ) = StockUseCase(stockRepository, localRepository, preferenceManager,intaDayParser)

    @Provides
    @Singleton
    fun provideIntraDayParser(): CSVParser<IntraDayInfo> {
        return IntraDayParser()
    }
}