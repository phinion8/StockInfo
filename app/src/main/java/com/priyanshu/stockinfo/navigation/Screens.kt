package com.priyanshu.stockinfo.navigation

sealed class Screens(val route: String) {
    data object Splash: Screens(route = Routes.SPLASH_SCREEN)
    data object OnBoarding: Screens(route = Routes.ON_BOARDING_SCREEN)
    data object Home: Screens(route = Routes.HOME_SCREENS)
    data object Watchlist: Screens(route = Routes.WATCHLIST_SCREEN)
}