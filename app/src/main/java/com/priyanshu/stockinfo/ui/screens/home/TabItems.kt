package com.priyanshu.stockinfo.ui.screens.home

sealed class TabItems(val id: String) {
    data object TopGainersTab : TabItems(id = "top_gainers")
    data object TopLosersTab : TabItems(id = "top_losers")
}