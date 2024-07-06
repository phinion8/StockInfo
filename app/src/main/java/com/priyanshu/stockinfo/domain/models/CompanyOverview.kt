package com.priyanshu.stockinfo.domain.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "company_overview_table",
    indices = [Index(value = ["Symbol"], unique = true)]
)
data class CompanyOverview(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val Symbol: String,
    @SerializedName("200DayMovingAverage")
    val twoHundredDayMovingAverage: String,
    @SerializedName("50DayMovingAverage")
    val fiftyDayMovingAverage: String,
    @SerializedName("52WeekHigh")
    val fiftyTwoWeeksHigh: String,
    @SerializedName("52WeekLow")
    val fiftyTwoWeeksLow: String,
    val Address: String,
    val AnalystRatingBuy: String,
    val AnalystRatingHold: String,
    val AnalystRatingSell: String,
    val AnalystRatingStrongBuy: String,
    val AnalystRatingStrongSell: String,
    val AnalystTargetPrice: String,
    val AssetType: String,
    val Beta: String,
    val BookValue: String,
    val CIK: String,
    val Country: String,
    val Currency: String,
    val Description: String,
    val DilutedEPSTTM: String,
    val DividendDate: String,
    val DividendPerShare: String,
    val DividendYield: String,
    val EBITDA: String,
    val EPS: String,
    val EVToEBITDA: String,
    val EVToRevenue: String,
    val ExDividendDate: String,
    val Exchange: String,
    val FiscalYearEnd: String,
    val ForwardPE: String,
    val GrossProfitTTM: String,
    val Industry: String,
    val LatestQuarter: String,
    val MarketCapitalization: String,
    val Name: String,
    val OperatingMarginTTM: String,
    val PEGRatio: String,
    val PERatio: String,
    val PriceToBookRatio: String,
    val PriceToSalesRatioTTM: String,
    val ProfitMargin: String,
    val QuarterlyEarningsGrowthYOY: String,
    val QuarterlyRevenueGrowthYOY: String,
    val ReturnOnAssetsTTM: String,
    val ReturnOnEquityTTM: String,
    val RevenuePerShareTTM: String,
    val RevenueTTM: String,
    val Sector: String,
    val SharesOutstanding: String,
    val TrailingPE: String,
    val Information: String? = null,
    val localCreationTime: Long = System.currentTimeMillis()
): Serializable