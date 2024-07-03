package com.priyanshu.stockinfo.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.priyanshu.stockinfo.navigation.Screens
import com.priyanshu.stockinfo.R
import com.priyanshu.stockinfo.navigation.Routes
import com.priyanshu.stockinfo.ui.screens.splash.viewModel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val isOnBoardingCompleted by viewModel.isOnBoardingCompleted.collectAsState()

    LaunchedEffect(key1 = Unit) {
        delay(1500)
        if (isOnBoardingCompleted) {
            navController.popBackStack()
            navController.navigate(Routes.HOME_GRAPH)
        } else {
            navController.popBackStack()
            navController.navigate(Screens.OnBoarding.route)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo"
        )
    }
}