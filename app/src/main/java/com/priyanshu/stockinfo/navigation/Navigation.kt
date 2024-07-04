package com.priyanshu.stockinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.priyanshu.stockinfo.ui.screens.home.HomeScreen
import com.priyanshu.stockinfo.ui.screens.home.HomeScreenContent
import com.priyanshu.stockinfo.ui.screens.onboarding.OnBoardingScreen
import com.priyanshu.stockinfo.ui.screens.overview.CompanyOverviewScreen
import com.priyanshu.stockinfo.ui.screens.splash.SplashScreen
import com.priyanshu.stockinfo.ui.screens.watchlist.WatchListScreen

@Composable
fun SetUpNavigation(
    navController: NavHostController
) {

    NavHost(navController = navController, route = "root", startDestination = "auth") {

        navigation(startDestination = Screens.Splash.route, route = "auth") {

            composable(route = Screens.Splash.route) {
                SplashScreen(navController)
            }

            composable(route = Screens.OnBoarding.route) {
                OnBoardingScreen(navController)
            }


        }

        composable(route = Routes.HOME_GRAPH) {
            HomeScreen()
        }

    }

}

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    showBottomBar: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        route = Routes.HOME_GRAPH,
        startDestination = Screens.Home.route
    ) {
        composable(route = Screens.Home.route) {
            showBottomBar(true)
            HomeScreenContent(innerPadding, navController = navController)
        }
        composable(route = Screens.Watchlist.route) {
            showBottomBar(true)
            WatchListScreen(innerPadding = innerPadding)
        }
        composable(
            route = Screens.CompanyOverview(null).buildRoute(), arguments = listOf(
                navArgument("ticker") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val ticker = navBackStackEntry.arguments?.getString("ticker")
            showBottomBar(false)
            if (ticker != null) {
                CompanyOverviewScreen(ticker)
            }
        }
    }
}