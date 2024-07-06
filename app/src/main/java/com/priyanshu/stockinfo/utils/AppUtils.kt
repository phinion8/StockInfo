package com.priyanshu.stockinfo.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDateTime

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun LazyGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

object AppUtils{
    fun formatMarketCap(value: String?): String? {
        value?.toLongOrNull()?.let {
            return when {
                it >= 1_000_000_000_000 -> String.format("%.2fT", it / 1_000_000_000_000.0)
                it >= 1_000_000_000 -> String.format("%.2fB", it / 1_000_000_000.0)
                it >= 1_000_000 -> String.format("%.2fM", it / 1_000_000.0)
                else -> it.toString()
            }
        }
        return null
    }

    fun getHourFromDateTime(dateTime: LocalDateTime): Int {
        return dateTime.hour
    }
}