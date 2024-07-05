package com.priyanshu.stockinfo.navigation

sealed class Screens(val route: String) {
    data object Splash : Screens(route = Routes.SPLASH_SCREEN)
    data object OnBoarding : Screens(route = Routes.ON_BOARDING_SCREEN)
    data object Home : Screens(route = Routes.HOME_SCREENS)
    data object Watchlist : Screens(route = Routes.WATCHLIST_SCREEN)
    data object Search: Screens(route = Routes.SEARCH_SCREEN)
    data class CompanyOverview(
        val ticker: String?
    ) : Screens(route = Routes.COMPANY_OVERVIEW_SCREEN) {
        fun buildRoute(): String {
            return if (ticker != null) {
                "${Routes.COMPANY_OVERVIEW_SCREEN}/${ticker}"
            } else {
                "${Routes.COMPANY_OVERVIEW_SCREEN}/{ticker}"
            }
        }
    }
}