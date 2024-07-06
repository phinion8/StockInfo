package com.priyanshu.stockinfo.domain.models.waitlist

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "waitlist_table",
    indices = [Index(value = ["symbol"], unique = true)]
)
data class WaitlistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val symbol: String,
    val name: String,
    val type: String
)