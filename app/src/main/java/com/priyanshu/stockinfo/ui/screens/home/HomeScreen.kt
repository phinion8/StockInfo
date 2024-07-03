package com.priyanshu.stockinfo.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.priyanshu.stockinfo.domain.models.TopGainerLoserItem
import com.priyanshu.stockinfo.navigation.HomeNavGraph
import com.priyanshu.stockinfo.ui.components.BottomNavBar
import com.priyanshu.stockinfo.ui.components.models.bottomNavItems
import com.priyanshu.stockinfo.ui.screens.home.components.HomeTabItem
import com.priyanshu.stockinfo.ui.screens.home.components.TopAppBar
import com.priyanshu.stockinfo.ui.screens.home.components.TopGainerLoserItem
import com.priyanshu.stockinfo.ui.screens.home.components.TopGainerLoserItemLoading
import com.priyanshu.stockinfo.ui.screens.home.viewModel.HomeViewModel
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
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var tabItemSelected by remember {
        mutableStateOf(TabItems.TopGainersTab.id)
    }
    val pagerState = rememberPagerState(
        pageCount = {
            3
        }
    )

    val isLoading by viewModel.isLoading.collectAsState()
    val topGainerAndLosers by viewModel.topGainersAndLosers.collectAsState()
    val scrollState = rememberScrollState()

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
                .padding(start = 8.dp)
                .horizontalScroll(scrollState)
        ) {
            HomeTabItem(tabTitle = "Top Gainers", onItemClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(0)
                    scrollState.scrollTo(0)
                }
            }, isItemSelected = tabItemSelected == TabItems.TopGainersTab.id)
            Spacer(modifier = Modifier.width(12.dp))
            HomeTabItem(tabTitle = "Top Losers", onItemClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(1)
                }
            }, isItemSelected = tabItemSelected == TabItems.TopLosersTab.id)
            Spacer(modifier = Modifier.width(12.dp))
            HomeTabItem(tabTitle = "Most Traded", onItemClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(2)
                    scrollState.scrollTo(100)
                }
            }, isItemSelected = tabItemSelected == TabItems.MostTraded.id)
        }

        if (isLoading) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(12) {
                    TopGainerLoserItemLoading()
                }
            }
        } else {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
                userScrollEnabled = true
            ) { index ->
                when (index) {
                    0 -> {
                        topGainerAndLosers?.let {
                            TopGainersLosersTab(
                                stockList = it.top_gainers
                            )
                        }
                    }

                    1 -> {
                        topGainerAndLosers?.let {
                            TopGainersLosersTab(
                                stockList = it.top_losers
                            )
                        }
                    }

                    2 -> {
                        topGainerAndLosers?.most_actively_traded?.let { TopGainersLosersTab(stockList = it) }
                    }
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 ->{
                tabItemSelected = TabItems.TopGainersTab.id
                scrollState.scrollTo(0)
            }
            1 -> {
                tabItemSelected = TabItems.TopLosersTab.id
            }
            2 ->{
                tabItemSelected = TabItems.MostTraded.id
                scrollState.scrollTo(100)
            }
            else -> tabItemSelected
        }
    }
}


@Composable
fun TopGainersLosersTab(
    stockList: List<TopGainerLoserItem>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(stockList) {
            TopGainerLoserItem(topGainerLoserItem = it)
        }
    }
}