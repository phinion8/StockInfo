package com.priyanshu.stockinfo.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.priyanshu.stockinfo.ui.components.shimmerLoadingAnimation

@Composable
fun TopGainerLoserItemLoading(modifier: Modifier = Modifier) {

    Box(modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxWidth(0.4f)
        .height(150.dp)
        .clip(RoundedCornerShape(16.dp))
        .shimmerLoadingAnimation())

}