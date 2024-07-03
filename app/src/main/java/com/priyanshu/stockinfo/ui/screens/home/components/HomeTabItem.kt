package com.priyanshu.stockinfo.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priyanshu.stockinfo.ui.theme.gray300
import com.priyanshu.stockinfo.ui.theme.gray500
import com.priyanshu.stockinfo.ui.theme.grayShade2
import com.priyanshu.stockinfo.ui.theme.green
import com.priyanshu.stockinfo.ui.theme.lightGray
import com.priyanshu.stockinfo.ui.theme.white

@Composable
fun HomeTabItem(
    tabTitle: String,
    isItemSelected: Boolean = false,
    onItemClick: () -> Unit
) {

    
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = if (isItemSelected) white else gray300, shape = CircleShape)
            .background(
                if (isItemSelected) gray500 else MaterialTheme.colorScheme.background,
                shape = CircleShape
            )
            .clickable {
                onItemClick()
            }
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Text(text = tabTitle, style = MaterialTheme.typography.bodyMedium)

    }

}

@Preview
@Composable
private fun HomeTabItemPreview() {
    HomeTabItem(tabTitle = "Top Gainers", onItemClick = {})
}