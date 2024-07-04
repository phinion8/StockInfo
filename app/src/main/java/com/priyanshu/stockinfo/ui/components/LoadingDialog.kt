package com.priyanshu.stockinfo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.priyanshu.stockinfo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(onDismissRequest: () -> Unit) {
    BasicAlertDialog(onDismissRequest = {
        onDismissRequest()
    }, properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent), contentAlignment = Alignment.Center) {
            ShowLottieAnimation(rawRes = R.raw.loading_anim, modifier = Modifier.size(150.dp))
        }
    }
}