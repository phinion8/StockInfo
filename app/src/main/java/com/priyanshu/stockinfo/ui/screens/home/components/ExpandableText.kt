package com.priyanshu.stockinfo.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ExpandableText(
    modifier: Modifier,
    maxLine: Int = 3,
    textStyle: TextStyle,
    text: String,
    readMoreTextStyle: TextStyle
) {

    val minimumLineLength = maxLine
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else minimumLineLength

    Column(modifier = modifier) {
        Text(
            text = text,
            style = textStyle,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                if (textLayoutResult.lineCount > minimumLineLength - 1) {  
                    if (textLayoutResult.isLineEllipsized(minimumLineLength - 1)) showReadMoreButtonState =
                        true
                }
            }
        )
        if (showReadMoreButtonState) {

            Text(
                text = if (expandedState) "Read Less" else "Read More",
                modifier = Modifier.clickable(
                    onClick = {
                        expandedState = !expandedState
                    }
                ).fillMaxWidth(),
                style = readMoreTextStyle
            )
        }

    }
}
