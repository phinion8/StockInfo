package com.priyanshu.stockinfo.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_entity_table",
    indices = [Index(value = ["symbol"], unique = true)]
)
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val symbol: String,
    val name: String,
    val type: String,
    val createdAt: Long
)