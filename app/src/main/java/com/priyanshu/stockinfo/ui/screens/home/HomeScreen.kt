package com.priyanshu.stockinfo.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.priyanshu.stockinfo.navigation.HomeNavGraph
import com.priyanshu.stockinfo.ui.components.BottomNavBar
import com.priyanshu.stockinfo.ui.components.models.bottomNavItems
import com.priyanshu.stockinfo.ui.screens.home.components.HomeTabItem
import com.priyanshu.stockinfo.ui.screens.home.components.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController()
) {
    var bottomBarVisibility by remember {
        mutableStateOf(false)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold(
            content = { innerPadding ->

                HomeNavGraph(
                    navController = navController,
                    innerPadding = innerPadding,
                    showBottomBar = {
                        bottomBarVisibility = it
                    })

            }, bottomBar = {
                if (bottomBarVisibility) {
                    BottomNavBar(
                        modifier = Modifier.fillMaxWidth(),
                        bottomNavItems = bottomNavItems,
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                this.launchSingleTop = true
                                this.restoreState = true
                            }
                        }
                    )
                }
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    innerPadding: PaddingValues
) {

    val coroutineScope = rememberCoroutineScope()
    var tabItemSelected by remember {
        mutableStateOf(TabItems.TopGainersTab.id)
    }
    val pagerState = rememberPagerState(
        pageCount = {
            2
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = innerPadding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(title = "Stocks")
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {

            HomeTabItem(tabTitle = "Top Gainers", onItemClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(0)
                    tabItemSelected = TabItems.TopGainersTab.id
                }
            }, isItemSelected = tabItemSelected == TabItems.TopGainersTab.id)
            Spacer(modifier = Modifier.width(16.dp))
            HomeTabItem(tabTitle = "Top Losers", onItemClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(1)
                    tabItemSelected = TabItems.TopLosersTab.id
                }
            }, isItemSelected = tabItemSelected == TabItems.TopLosersTab.id)

        }

        HorizontalPager(state = pagerState) {
            when (pagerState.pageCount) {
                0 -> {
                    tabItemSelected = TabItems.TopGainersTab.id
                    TopGainersScreen()
                }

                1 -> {
                    tabItemSelected = TabItems.TopLosersTab.id
                    TopLosersScreen()
                }
            }
        }

    }
}

@Composable
fun TopGainersScreen(modifier: Modifier = Modifier) {

}

@Composable
fun TopLosersScreen(modifier: Modifier = Modifier) {

}