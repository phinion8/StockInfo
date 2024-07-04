package com.priyanshu.stockinfo.ui.screens.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CompanyOverviewScreen(
    ticker: String
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 8.dp, horizontal = 16.dp)) {

        Text(text = ticker)

    }

}