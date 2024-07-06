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
    val Symbol: String? = null,
    @SerializedName("200DayMovingAverage")
    val twoHundredDayMovingAverage: String? = null,
    @SerializedName("50DayMovingAverage")
    val fiftyDayMovingAverage: String? = null,
    @SerializedName("52WeekHigh")
    val fiftyTwoWeeksHigh: String? = null,
    @SerializedName("52WeekLow")
    val fiftyTwoWeeksLow: String? = null,
    val Address: String? = null,
    val AnalystRatingBuy: String? = null,
    val AnalystRatingHold: String? = null,
    val AnalystRatingSell: String? = null,
    val AnalystRatingStrongBuy: String? = null,
    val AnalystRatingStrongSell: String? = null,
    val AnalystTargetPrice: String? = null,
    val AssetType: String? = null,
    val Beta: String? = null,
    val BookValue: String? = null,
    val CIK: String? = null,
    val Country: String? = null,
    val Currency: String? = null,
    val Description: String? = null,
    val DilutedEPSTTM: String? = null,
    val DividendDate: String? = null,
    val DividendPerShare: String? = null,
    val DividendYield: String? = null,
    val EBITDA: String? = null,
    val EPS: String? = null,
    val EVToEBITDA: String? = null,
    val EVToRevenue: String? = null,
    val ExDividendDate: String? = null,
    val Exchange: String? = null,
    val FiscalYearEnd: String? = null,
    val ForwardPE: String? = null,
    val GrossProfitTTM: String? = null,
    val Industry: String? = null,
    val LatestQuarter: String? = null,
    val MarketCapitalization: String? = null,
    val Name: String? = null,
    val OperatingMarginTTM: String? = null,
    val PEGRatio: String? = null,
    val PERatio: String? = null,
    val PriceToBookRatio: String? = null,
    val PriceToSalesRatioTTM: String? = null,
    val ProfitMargin: String? = null,
    val QuarterlyEarningsGrowthYOY: String? = null,
    val QuarterlyRevenueGrowthYOY: String? = null,
    val ReturnOnAssetsTTM: String? = null,
    val ReturnOnEquityTTM: String? = null,
    val RevenuePerShareTTM: String? = null,
    val RevenueTTM: String? = null,
    val Sector: String? = null,
    val SharesOutstanding: String? = null,
    val TrailingPE: String? = null,
    val Information: String? = null,
    val localCreationTime: Long = System.currentTimeMillis()
): Serializable
