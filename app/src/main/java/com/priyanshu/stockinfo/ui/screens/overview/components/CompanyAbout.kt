package com.priyanshu.stockinfo.ui.screens.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.stockinfo.ui.screens.home.components.ExpandableText
import com.priyanshu.stockinfo.ui.theme.blue
import com.priyanshu.stockinfo.ui.theme.lightGray
import com.priyanshu.stockinfo.ui.theme.primaryColor
import com.priyanshu.stockinfo.ui.theme.white
import com.priyanshu.stockinfo.utils.AppUtils

@Composable
fun CompanyAbout(
    title: String,
    about: String,
    industry: String,
    sector: String,
    `52WeekLow`: String,
    `52WeekHigh`: String,
    currentPrice: String,
    marketCap: String,
    peRatio: String,
    beta: String,
    dividendYield: String,
    profitMargin: String,
    pbRatio: String
) {

    val price by remember {
        mutableFloatStateOf(currentPrice.toFloat())
    }
    val high by remember {
        mutableFloatStateOf(`52WeekHigh`.toFloat())
    }
    val low by remember {
        mutableFloatStateOf(`52WeekLow`.toFloat())
    }
    val total by remember {
        derivedStateOf { low + high }
    }
    val plot by remember {
        derivedStateOf { price / total }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(width = 1.dp, color = lightGray, shape = RoundedCornerShape(16.dp))
            .padding(bottom = 8.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )

        HorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = lightGray)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            ExpandableText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = about,
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp,
                    lineHeight = 24.sp
                ),
                readMoreTextStyle = MaterialTheme.typography.bodyMedium.copy(
                    primaryColor,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                ),
                maxLine = 5,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                ItemChip(title = industry)
                Spacer(modifier = Modifier.height(16.dp))
                ItemChip(title = sector)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "52-Week Low",
                    style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Start)
                )

                Text(
                    text = "52-Week High",
                    style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.End)
                )

            }



            Spacer(modifier = Modifier.height(4.dp))



            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = `52WeekLow`,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold, fontSize = 14.sp
                    )
                )

                Text(
                    text = `52WeekHigh`,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )

            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(plot),
                text = "${currentPrice}\n▼",
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 16.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.SemiBold
                )
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(), thickness = 2.dp, color = lightGray
            )


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppUtils.formatMarketCap(marketCap)
                    ?.let { InfoItem(title = "Market Cap", value = it) }
                InfoItem(title = "P/E Ratio", value = peRatio)
                InfoItem(title = "Beta", value = beta)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(title = "Dividend Yield", value = dividendYield)
                InfoItem(title = "Profit Margin", value = profitMargin)
                InfoItem(title = "P/B Ratio", value = pbRatio)
            }
        }

    }
}

@Composable
fun InfoItem(
    title: String,
    value: String
) {

    Column(
        modifier = Modifier.fillMaxWidth(0.25f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(color = lightGray)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
    }

}

@Composable
fun ItemChip(
    title: String
) {
    Box(
        modifier = Modifier
            .background(shape = CircleShape, color = blue)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium.copy(color = white))
    }
}