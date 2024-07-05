package com.priyanshu.stockinfo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.priyanshu.stockinfo.data.local.dao.Converters
import com.priyanshu.stockinfo.data.local.dao.StockDao
import com.priyanshu.stockinfo.data.local.entities.SearchEntity
import com.priyanshu.stockinfo.domain.models.TopGainerAndLosers
import com.priyanshu.stockinfo.domain.models.TopGainerLoserItem

@Database(entities = [TopGainerAndLosers::class, SearchEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StockDatabase: RoomDatabase() {
    abstract fun stockDao(): StockDao
}