package com.priyanshu.stockinfo.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.priyanshu.stockinfo.navigation.Screens
import com.priyanshu.stockinfo.R
import com.priyanshu.stockinfo.navigation.Routes
import com.priyanshu.stockinfo.ui.components.CustomElevatedButton
import com.priyanshu.stockinfo.ui.components.ShowLottieAnimation
import com.priyanshu.stockinfo.ui.screens.onboarding.viewModel.OnBoardingViewModel
import com.priyanshu.stockinfo.ui.theme.lightGray

@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        ShowLottieAnimation(rawRes = R.raw.onboarding_anim, modifier = Modifier.size(380.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Get real time insights of Stock Market",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Access real-time stock market information and make informed investment decisions with ease.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = lightGray
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(42.dp))

            CustomElevatedButton(onClick = {
                viewModel.saveOnBoardingState(completed = true)
                navController.popBackStack()
                navController.navigate(Routes.HOME_GRAPH)
            }, text = "Get Started")
        }

    }

}