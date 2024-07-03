package com.priyanshu.stockinfo.ui.components.models

import androidx.annotation.DrawableRes
import com.priyanshu.stockinfo.navigation.Screens
import com.priyanshu.stockinfo.R

data class BottomNavItem(
    @DrawableRes
    val icon: Int,
    val title: String,
    val route: String
)

val bottomNavItems = listOf<BottomNavItem>(
    BottomNavItem(
        icon = R.drawable.ic_home,
        title = "Home",
        route = Screens.Home.route
    ),
    BottomNavItem(
        icon = R.drawable.ic_bookmark,
        title = "Watchlist",
        route = Screens.Watchlist.route
    )
)