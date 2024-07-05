package com.priyanshu.stockinfo.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.priyanshu.stockinfo.R
import com.priyanshu.stockinfo.navigation.Screens
import com.priyanshu.stockinfo.ui.components.ShowLottieAnimation
import com.priyanshu.stockinfo.ui.screens.home.TabItems
import com.priyanshu.stockinfo.ui.screens.home.components.HomeTabItem
import com.priyanshu.stockinfo.ui.screens.search.components.ItemSearch
import com.priyanshu.stockinfo.ui.screens.search.components.ItemSearchLoading
import com.priyanshu.stockinfo.ui.screens.search.components.SearchChipItem
import com.priyanshu.stockinfo.ui.screens.search.components.SearchChipItems
import com.priyanshu.stockinfo.ui.screens.search.viewModel.SearchType
import com.priyanshu.stockinfo.ui.screens.search.viewModel.SearchViewModel
import com.priyanshu.stockinfo.ui.theme.gray300
import com.priyanshu.stockinfo.ui.theme.gray500
import com.priyanshu.stockinfo.ui.theme.lightGray
import com.priyanshu.stockinfo.ui.theme.primaryColor
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val searchQuery by viewModel.searchQuery
    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchListState by viewModel.searchListState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val scrollState = rememberScrollState()
    var chipItemSelected by remember {
        mutableStateOf(SearchChipItems.All.id)
    }
    val localSearchList by viewModel.localSearchListState.collectAsState()
    val searchList by viewModel.searchTypeListState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(gray500),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f)
                    .padding(start = 16.dp, end = 8.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = lightGray
            )
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                onValueChange = { viewModel.updateSearchQuery(it) },
                value = searchQuery,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = primaryColor),
                modifier = Modifier
                    .weight(5f)
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    keyboardController?.hide()

                    viewModel.searchTicker(searchQuery)

                }),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search Stocks",
                            style = MaterialTheme.typography.bodyLarge.copy(color = lightGray)
                        )
                    }
                    innerTextField()
                },
                cursorBrush = SolidColor(primaryColor)
            )
            if (searchQuery.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .weight(1f)
                        .padding(start = 16.dp, end = 8.dp)
                        .clickable {
                            viewModel.updateSearchQuery("")
                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Search icon",
                    tint = lightGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .horizontalScroll(scrollState)
        ) {
            SearchChipItem(chipTitle = "All", onItemClick = {

                chipItemSelected = SearchChipItems.All.id
                viewModel.updateSearchType(SearchType.All)
                viewModel.filterListByType(SearchType.All)

            }, isItemSelected = chipItemSelected == SearchChipItems.All.id)
            Spacer(modifier = Modifier.width(8.dp))
            SearchChipItem(chipTitle = "Stocks", onItemClick = {

                chipItemSelected = SearchChipItems.Stocks.id
                viewModel.updateSearchType(SearchType.Equity)
                viewModel.filterListByType(SearchType.Equity)

            }, isItemSelected = chipItemSelected == SearchChipItems.Stocks.id)
            Spacer(modifier = Modifier.width(8.dp))
            SearchChipItem(chipTitle = "ETFS", onItemClick = {

                chipItemSelected = SearchChipItems.Etfs.id
                viewModel.updateSearchType(SearchType.ETF)
                viewModel.filterListByType(SearchType.ETF)

            }, isItemSelected = chipItemSelected == SearchChipItems.Etfs.id)
            Spacer(modifier = Modifier.width(8.dp))
            SearchChipItem(chipTitle = "Mutual Funds", onItemClick = {

                chipItemSelected = SearchChipItems.MutualFunds.id
                viewModel.updateSearchType(SearchType.MutualFunds)
                viewModel.filterListByType(SearchType.MutualFunds)

            }, isItemSelected = chipItemSelected == SearchChipItems.MutualFunds.id)
        }

        if (
            !isLoading && searchListState != null &&
            searchListState?.bestMatches?.isEmpty() == true
        ) {

            ErrorLayout(error = "No stocks found")

        } else if (error != null) {

            ErrorLayout(error = error)

        } else if (isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ShowLottieAnimation(
                    rawRes = R.raw.search_anim,
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.Center)
                )
            }

        } else {
            if (localSearchList.isNotEmpty() && chipItemSelected == SearchChipItems.All.id && searchListState == null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(localSearchList) {
                        ItemSearch(
                            name = it.name,
                            symbol = it.symbol,
                            type = it.type,
                            isLocalSearchItem = true,
                            onItemClick = {ticker->

                                navController.navigate(
                                    Screens.CompanyOverview(
                                        ticker = ticker
                                    ).buildRoute()
                                )

                            }
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(searchList) { item ->
                    ItemSearch(
                        name = item.name,
                        symbol = item.symbol,
                        type = item.type,
                        onItemClick = {ticker->
                            viewModel.addSearchEntity(
                                symbol = item.symbol,
                                name = item.name,
                                type = item.type
                            )
                            navController.navigate(
                                Screens.CompanyOverview(
                                    ticker = ticker
                                ).buildRoute()
                            )
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun ErrorLayout(error: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(92.dp),
            painter = painterResource(id = R.drawable.ic_sad),
            contentDescription = "Search icon",
            tint = lightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = error ?: "Something went wrong",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = lightGray,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        )
    }
}