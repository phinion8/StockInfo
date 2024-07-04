package com.priyanshu.stockinfo.navigation

sealed class Screens(val route: String) {
    data object Splash : Screens(route = Routes.SPLASH_SCREEN)
    data object OnBoarding : Screens(route = Routes.ON_BOARDING_SCREEN)
    data object Home : Screens(route = Routes.HOME_SCREENS)
    data object Watchlist : Screens(route = Routes.WATCHLIST_SCREEN)
    data class CompanyOverview(
        val ticker: String?,
        val price: String?,
        val change_amount: String?,
        val change_percentage: String?,
        val volume: String?
    ) : Screens(route = Routes.COMPANY_OVERVIEW_SCREEN) {
        fun buildRoute(): String {
            return if (ticker != null) {
                "${Routes.COMPANY_OVERVIEW_SCREEN}/${ticker}/${price}/${change_amount}/${change_percentage.toString()}/${volume}"
            } else {
                "${Routes.COMPANY_OVERVIEW_SCREEN}/{ticker}/{price}/{change_amount}/{change_percentage}/{volume}"
            }
        }
    }
}