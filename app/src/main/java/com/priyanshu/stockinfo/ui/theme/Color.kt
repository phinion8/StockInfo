package com.priyanshu.stockinfo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val white = Color(0xFFFFFFFF)
val darkBlue = Color(0xFF0c0f14)
val blue = Color(0xFF5E76FF)
val darkGray = Color(0xFF161616)
val lightGray = Color(0xFF868686)
val grayShade2 = Color(0xFFE6E6E6)
val green = Color(0xFF48d8a3)
val red = Color(0xFFEE4E4E)


val primaryColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) white else darkBlue

val secondaryColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) darkBlue else white

val gray: Color
    @Composable
    get() = if (isSystemInDarkTheme()) darkGray else grayShade2

val gray500: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF272727) else grayShade2

val gray300: Color
    @Composable
    get() = if (isSystemInDarkTheme()) lightGray else grayShade2