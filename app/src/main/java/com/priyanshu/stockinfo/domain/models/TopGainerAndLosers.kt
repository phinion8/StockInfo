package com.priyanshu.stockinfo.domain.models

data class TopGainerAndLosers(
    val last_updated: String,
    val metadata: String,
    val most_actively_traded: List<MostActivelyTraded>,
    val top_gainers: List<TopGainerLoserItem>,
    val top_losers: List<TopGainerLoserItem>
)