package com.priyanshu.stockinfo.ui.screens.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.priyanshu.stockinfo.domain.models.IntraDayInfo
import com.priyanshu.stockinfo.ui.components.LoadingDialog
import com.priyanshu.stockinfo.ui.screens.overview.components.CompanyAbout
import com.priyanshu.stockinfo.ui.screens.overview.viewModel.CompanyOverviewViewModel
import com.priyanshu.stockinfo.ui.theme.blue
import com.priyanshu.stockinfo.ui.theme.green
import com.priyanshu.stockinfo.ui.theme.lightGray
import com.priyanshu.stockinfo.ui.theme.primaryColor
import com.priyanshu.stockinfo.ui.theme.red
import com.priyanshu.stockinfo.ui.theme.white
import com.priyanshu.stockinfo.utils.AppUtils

@Composable
fun CompanyOverviewScreen(
    ticker: String,
    viewModel: CompanyOverviewViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getCompanyOverview("IBM")
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getIntraDayInfoList("AAPL")
    }

    val companyOverview by viewModel.companyOverviewState.collectAsState()
    val intraDayInfoList by viewModel.intraDayInfoListState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val scrollState = rememberScrollState()
    var currentPrice by remember {
        mutableStateOf(0.0)
    }
    var openingPrice by remember {
        mutableStateOf(0.0)
    }
    val percentage by remember {
        derivedStateOf {
            ((currentPrice - openingPrice) / openingPrice ) * 100
        }
    }

    if (intraDayInfoList.isNotEmpty()) {
        currentPrice = intraDayInfoList[intraDayInfoList.size - 1].close
        openingPrice = intraDayInfoList[0].close
    }

    if (isLoading) {
        LoadingDialog(
            onDismissRequest = {

            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back button",
            tint = primaryColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!isLoading) {
            if (companyOverview != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        companyOverview?.let {
                            Text(
                                text = it.Name,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontSize = 18.sp,
                                    lineHeight = 24.sp
                                )
                            )
                        }
                        Text(
                            text = "(${companyOverview?.Symbol}) ${companyOverview?.AssetType}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = lightGray)
                        )
                        companyOverview?.let {
                            Text(
                                text = it.Exchange,
                                style = MaterialTheme.typography.bodyMedium.copy(color = lightGray)
                            )
                        }
                    }

                    if (currentPrice != 0.0){
                        Column {
                            Text(
                                text = "$$currentPrice",
                                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 18.sp)
                            )
                            Text(
                                text = if (percentage > 0) ("+${String.format("%.2f", percentage).toFloat()}%▲") else "${
                                    percentage
                                }%▼",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (percentage > 0) green else red
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CompanyChart(intraDayInfoList = intraDayInfoList, isLoading = isLoading)

                Spacer(modifier = Modifier.height(16.dp))

                CompanyAbout(
                    title = "About ${companyOverview!!.Name}",
                    about = companyOverview!!.Description,
                    industry = companyOverview!!.Industry,
                    sector = companyOverview!!.Sector,
                    `52WeekLow` = "50",
                    `52WeekHigh` = "228",
                    currentPrice = "110",
                    marketCap = companyOverview!!.MarketCapitalization,
                    peRatio = companyOverview!!.PERatio,
                    beta = companyOverview!!.Beta,
                    dividendYield = companyOverview!!.DividendYield,
                    profitMargin = companyOverview!!.ProfitMargin,
                    pbRatio = companyOverview!!.PriceToBookRatio
                )

            }

        } else if (error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = error.toString(),
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = lightGray,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }


    }

}

@Composable
fun CompanyChart(
    isLoading: Boolean,
    intraDayInfoList: List<IntraDayInfo>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = lightGray, shape = RoundedCornerShape(16.dp))
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!isLoading && intraDayInfoList.isNotEmpty()) {
            DayChart(intraDayInfoList = intraDayInfoList)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = blue)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .clip(CircleShape)
                .border(width = 1.dp, color = lightGray, shape = CircleShape)
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ChatItemTab(title = "1D", selected = true)
        }
    }

}


@Composable
fun ChatItemTab(
    title: String,
    selected: Boolean
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(35.dp)
            .clip(CircleShape)
            .background(if (selected) blue else Color.Transparent, shape = CircleShape)
            .padding(all = 6.dp), contentAlignment = Alignment.Center
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                if (selected) white else primaryColor,
                textAlign = TextAlign.Center
            )
        )

    }

}

@Composable
fun DayChart(
    intraDayInfoList: List<IntraDayInfo>
) {
    val steps = intraDayInfoList.size
    val yPointsData = ArrayList<Float>()
    val xPointsData = ArrayList<Float>()
    intraDayInfoList.map {item->
        yPointsData.add(item.close.toFloat())
        xPointsData.add(AppUtils.getHourFromDateTime(item.date).toFloat())
    }
    yPointsData.sort()
    val pointsData = intraDayInfoList.map { intraDayInfo ->
        Point(
            AppUtils.getHourFromDateTime(intraDayInfo.date).toFloat(),
            intraDayInfo.close.toFloat(),
        )
    }

    val yAxisData = AxisData.Builder().steps(steps - 1).backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp).labelData { i ->
            yPointsData[i].toString()
        }.axisLineColor(blue).axisLabelColor(blue).axisLabelFontSize(10.sp).build()

    val xAxisData = AxisData.Builder().axisStepSize(100.dp).backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1).labelData { i ->
            intraDayInfoList[i].date.hour.toString() + ":00"
        }.labelAndAxisLinePadding(15.dp)
        .axisLineColor(blue).axisLabelColor(blue).axisLabelFontSize(10.sp).build()


    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        lineType = LineType.SmoothCurve(isDotted = false),
                        color = blue,
                        width = 4f
                    ),
                    intersectionPoint = IntersectionPoint(color = blue, radius = 2.dp),
                    SelectionHighlightPoint(color = blue, radius = 2.dp),
                    ShadowUnderLine(
                        alpha = 0.5f, brush = Brush.verticalGradient(
                            colors = listOf(
                                blue, Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp(
                        backgroundColor = Color.Transparent, labelColor = primaryColor
                    )
                )
            )
        ),
        backgroundColor = Color.Transparent,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(Color.Transparent),
        accessibilityConfig = AccessibilityConfig(
            dividerColor = Color.Transparent
        ),

        )

    LineChart(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        lineChartData = lineChartData
    )
}