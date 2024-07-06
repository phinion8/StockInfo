package com.priyanshu.stockinfo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun NetworkConnectivityObserver(
    onNetworkStatusChanged: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    var isConnected by remember { mutableStateOf(true) }

    DisposableEffect(lifecycleOwner.value) {
        val networkChangeReceiver = NetworkChangeReceiver { isNetworkConnected ->
            isConnected = isNetworkConnected
            onNetworkStatusChanged(isNetworkConnected)
        }

        val lifecycle = lifecycleOwner.value.lifecycle
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkChangeReceiver, intentFilter)

        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                context.unregisterReceiver(networkChangeReceiver)
            }
        }

        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
}
