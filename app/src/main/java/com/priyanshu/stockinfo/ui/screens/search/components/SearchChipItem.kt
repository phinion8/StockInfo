package com.priyanshu.stockinfo.ui.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.stockinfo.ui.theme.gray300
import com.priyanshu.stockinfo.ui.theme.gray500
import com.priyanshu.stockinfo.ui.theme.green
import com.priyanshu.stockinfo.ui.theme.lightGray
import com.priyanshu.stockinfo.ui.theme.white

@Composable
fun SearchChipItem(
    chipTitle: String,
    isItemSelected: Boolean = false,
    onItemClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isItemSelected) white else lightGray.copy(alpha = 0.8f),
                shape = CircleShape
            )
            .background(
                if (isItemSelected) green.copy(alpha = 0.8f) else MaterialTheme.colorScheme.background,
                shape = CircleShape
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable {
                onItemClick()
            }
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = chipTitle,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = if (isItemSelected) white else lightGray
            )
        )

    }


}