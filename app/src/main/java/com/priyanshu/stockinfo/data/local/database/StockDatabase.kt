package com.priyanshu.stockinfo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.priyanshu.stockinfo.data.local.dao.Converters
import com.priyanshu.stockinfo.data.local.dao.StockDao
import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.CompanyOverview
import com.priyanshu.stockinfo.domain.models.IntraDayGraphEntity
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.TopGainerLoserItem
import com.priyanshu.stockinfo.domain.models.waitlist.WaitlistEntity

@Database(
    entities = [TopGainerAndLosers::class, SearchEntity::class, CompanyOverview::class, WaitlistEntity::class, IntraDayGraphEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}