package com.priyanshu.stockinfo.ui.screens.watchlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.priyanshu.stockinfo.R
import com.priyanshu.stockinfo.navigation.Screens
import com.priyanshu.stockinfo.ui.screens.home.components.TopAppBar
import com.priyanshu.stockinfo.ui.screens.home.components.TopGainerLoserItem
import com.priyanshu.stockinfo.ui.screens.search.ErrorLayout
import com.priyanshu.stockinfo.ui.screens.watchlist.components.ItemWaitlist
import com.priyanshu.stockinfo.ui.screens.watchlist.viewModel.WaitlistViewModel
import com.priyanshu.stockinfo.ui.theme.gray500
import com.priyanshu.stockinfo.ui.theme.green
import com.priyanshu.stockinfo.ui.theme.lightGray
import com.priyanshu.stockinfo.ui.theme.primaryColor
import com.priyanshu.stockinfo.utils.isScrollingUp

@Composable
fun WatchListScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: WaitlistViewModel = hiltViewModel()
) {
    val state = rememberLazyGridState()
    val isScrollingUp = state.isScrollingUp()
    val waitlist by viewModel.waitListState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val searchQuery by viewModel.searchQuery
    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getWaitlistEntityList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = innerPadding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isScrollingUp,
            exit = shrinkVertically(),
            enter = expandVertically()
        ) {
            TopAppBar(title = "Watchlist", onSearchClick = {
                navController.navigate(Screens.Search.route)
            })

        }

        Spacer(modifier = Modifier.height(16.dp))

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
                onValueChange = {
                    viewModel.updateSearchQuery(it)
                    viewModel.getLocalSearchWaitlist()
                },
                value = searchQuery,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = primaryColor),
                modifier = Modifier
                    .weight(5f)
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    keyboardController?.hide()
                    viewModel.getLocalSearchWaitlist()
                }),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search Waitlist",
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
                            viewModel.getLocalSearchWaitlist()

                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear icon",
                    tint = lightGray
                )
            }
        }

        if (waitlist.isEmpty() && !isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .alpha(0.8f),
                    painter = painterResource(id = R.drawable.empty_box),
                    contentDescription = "Empty list"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No Item Found",
                    style = MaterialTheme.typography.bodyLarge.copy(color = lightGray)
                )

            }
        }

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            columns = GridCells.Fixed(2),
            state = state
        ) {
            items(waitlist) {
                ItemWaitlist(waitlistEntity = it, onItemClick = { symbol ->
                    navController.navigate(Screens.CompanyOverview(symbol).buildRoute())
                })
            }
        }

    }
}