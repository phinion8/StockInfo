package com.priyanshu.stockinfo.ui.screens.watchlist.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.priyanshu.stockinfo.domain.models.TopGainerLoserItem
import com.priyanshu.stockinfo.domain.models.waitlist.WaitlistEntity
import com.priyanshu.stockinfo.ui.screens.watchlist.viewModel.WaitlistViewModel
import com.priyanshu.stockinfo.ui.theme.gray300
import com.priyanshu.stockinfo.ui.theme.green
import com.priyanshu.stockinfo.ui.theme.lightGray
import com.priyanshu.stockinfo.ui.theme.red

@Composable
fun ItemWaitlist(
    waitlistEntity: WaitlistEntity,
    onItemClick: (symbol: String) -> Unit
) {
    Column(
        Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(0.4f)
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = gray300, shape = RoundedCornerShape(16.dp))
            .padding(all = 12.dp)
            .clickable {
                onItemClick(waitlistEntity.symbol)
            },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = waitlistEntity.name,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )

        Text(text = waitlistEntity.symbol, style = MaterialTheme.typography.bodyMedium)

        Text(
            text = waitlistEntity.type,
            style = MaterialTheme.typography.bodyMedium.copy(color = lightGray)
        )
    }

}

