package com.priyanshu.stockinfo.navigation

import android.util.Log
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
            route = Screens.CompanyOverview(null, null, null, null, null).buildRoute(),
            arguments = listOf(
                navArgument("ticker") { type = NavType.StringType },
                navArgument("price") { type = NavType.StringType },
                navArgument("change_amount") { type = NavType.StringType },
                navArgument("change_percentage") { type = NavType.StringType },
                navArgument("volume") { type = NavType.StringType },
            ),
        ) { navBackStackEntry ->
            val ticker = navBackStackEntry.arguments?.getString("ticker")
            val price = navBackStackEntry.arguments?.getString("price")
            val changeAmount = navBackStackEntry.arguments?.getString("change_amount")
            val changePercentage = navBackStackEntry.arguments?.getString("change_percentage")
            val volume = navBackStackEntry.arguments?.getString("volume")
            showBottomBar(false)
            if (ticker != null && price != null && changePercentage != null && changeAmount != null && volume != null) {
                Log.d("SOMEISSUE", changePercentage)
                CompanyOverviewScreen(ticker, price, changeAmount, changePercentage, volume)
            }
        }
    }
}