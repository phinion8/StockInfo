package com.priyanshu.stockinfo.ui.screens.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.priyanshu.stockinfo.domain.models.TopGainerLoserItem
import com.priyanshu.stockinfo.ui.theme.gray300
import com.priyanshu.stockinfo.ui.theme.green
import com.priyanshu.stockinfo.ui.theme.red

@Composable
fun TopGainerLoserItem(
    topGainerLoserItem: TopGainerLoserItem,
    onItemClick: (TopGainerLoserItem) -> Unit
) {

    Column(
        Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(0.4f)
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = gray300, shape = RoundedCornerShape(16.dp))
            .padding(all = 12.dp)
            .clickable {
                onItemClick(topGainerLoserItem)
            },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = topGainerLoserItem.ticker,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )

        Text(text = topGainerLoserItem.price, style = MaterialTheme.typography.bodyLarge)

        Text(
            text = if (!topGainerLoserItem.change_amount.contains("-")) "▲ ${topGainerLoserItem.change_amount}" else "▼ ${topGainerLoserItem.change_amount}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (!topGainerLoserItem.change_amount.contains("-")) green else red
            )
        )

        Text(
            text = if (topGainerLoserItem.change_percentage.contains("-")) "${topGainerLoserItem.change_percentage}" else "+${topGainerLoserItem.change_percentage}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (!topGainerLoserItem.change_amount.contains("-")) green else red
            )
        )
    }

}