package com.priyanshu.stockinfo.ui.screens.search.components

sealed class SearchChipItems(val id: String) {
    data object All : SearchChipItems(id = "all")
    data object Stocks : SearchChipItems(id = "stocks")
    data object Etfs: SearchChipItems(id = "etfs")
    data object MutualFunds: SearchChipItems(id = "mutual_fund")
}