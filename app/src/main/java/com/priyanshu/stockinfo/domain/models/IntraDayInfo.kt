package com.priyanshu.stockinfo.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.priyanshu.stockinfo.data.local.dao.Converters
import com.priyanshu.stockinfo.utils.Constants
import java.time.LocalDateTime

data class IntraDayInfo(
    val date: LocalDateTime,
    val close: Double
)

@Entity(tableName = "intraday_info_table")
data class IntraDayInfoEntity(
    val hour: Int,
    val close: Double,
)

@Entity(tableName = "intraday_graph_table")
data class IntraDayGraphEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val symbol: String,
    @TypeConverters(Converters::class) val intraDayInfo: List<IntraDayInfoEntity>,
    val createdTime: Long
)