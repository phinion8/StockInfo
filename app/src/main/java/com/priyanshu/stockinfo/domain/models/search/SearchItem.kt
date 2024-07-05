package com.priyanshu.stockinfo.domain.models.search

data class SearchItem(
    val bestMatches: List<BestMatche>,
    val Information: String? = null
)