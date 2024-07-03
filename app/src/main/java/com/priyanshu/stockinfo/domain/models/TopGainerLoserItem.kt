package com.priyanshu.stockinfo.domain.models

data class TopGainerLoserItem(
    val change_amount: String,
    val change_percentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)